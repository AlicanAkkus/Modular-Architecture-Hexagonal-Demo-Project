#!/bin/sh

mkdir -p ~/logs/hexagonal-logs
LOG_FOLDER=~/logs/hexagonal-logs

apis="
,ticket-api:8080,
,payment-api:8090,
"

dbs="
,ticket-api:ticket-db,
,payment-api:payment-db,
"

################################
# BOOT DOWN FUNCTIONS
################################

down_infra() {
    docker-compose down
}

down_api() {
    api_name=$1
    # shellcheck disable=SC2009
    pid_list=$(ps ax | grep -i bootRun | grep -v grep | grep -i "$api_name" | awk '{print $1}')
    for pid in ${pid_list}
    do
        # shellcheck disable=SC2009
        details=$(ps -p "${pid}" | grep "${pid}" )
        kill -9 "${pid}"
        echo "\033[31mkilled: $pid :> \033[33m $details"
    done
}

down_apis() {
    # shellcheck disable=SC2009
    pid_list=$(ps ax | grep -i bootRun | grep -v grep | awk '{print $1}')
    for pid in ${pid_list}
    do
        # shellcheck disable=SC2009
        details=$(ps -p "${pid}" | grep "${pid}" )
        kill -9 "${pid}"
        echo "\033[31mkilled: $pid :> \033[33m $details"
    done
}

################################
# BOOT UP FUNCTIONS
################################

up_infra() {
    docker-compose up -d
}

up_apis() {
    echo "\033[37mBooting APIs ..."

    # shellcheck disable=SC2039
    split="${apis:1:${#apis}-2}"
    api_arr=$(echo "${split}" | tr "," "\n")
    for i in ${api_arr}
    do
        value="${i##*:}"
        key="${i%%:*}"
        up_api "${key}" "${value}"
    done
}

up_api() {
    api_name=$1
    server_port=$(get_api_port "$1")
    echo "\033[97m   $api_name (port:$server_port) ...\c"

    upFlag=$(isRunning "${server_port}")

    if [ "${upFlag}" = 1 ]; then
        echo "\033[36m already up"
        return 1
    fi

    startBoot=$(date +%s)

    mkdir -p ${LOG_FOLDER}
    touch ${LOG_FOLDER}/"${api_name}".log

    nohup bash -c "./gradlew $api_name:bootRun -Pargs=--spring.profiles.active=local,--server.port=$server_port" > ${LOG_FOLDER}/"${api_name}".log&

    upFlag=$(isUp "${server_port}")

    endBoot=$(date +%s)

    totalBoot=$((endBoot-startBoot))
    if [ "${upFlag}" = 1 ]; then
        echo "\033[32m done\033[36m (in ${totalBoot} sec)"
    else
        echo "\033[31m failed\033[36m (in ${totalBoot} sec)"
    fi
}

################################
# HELPER FUNCTIONS
################################

isDBUp() {
    x=1
    logs=$(docker container logs 2>/dev/null --tail 100 hexagonaldemo_mysql)
    # shellcheck disable=SC2039
    while [[ $logs != *"started."* ]] && [  ${x} -le 19 ]; do
        sleep 1;
        x=$(( x + 1 ))
        printf "."
        logs=$(docker container logs 2>/dev/null --tail 100 hexagonaldemo_mysql 2>&1)
    done

    logs=$(docker container logs 2>/dev/null --tail 100 hexagonaldemo_mysql 2>&1)
    # shellcheck disable=SC2039
    if [[ $logs != *"started."* ]]; then
        printf "DB cannot be boot up in %s seconds, quited." "$x"
        printf "\nLOGS:"
        docker container logs 2>/dev/null --tail 100 hexagonaldemo_mysql 2>&1
        exit 1
    else
        printf "DB becomes up and running in %s seconds\n" "$x"
    fi
}

prepare_log_files() {
    mkdir -p ${LOG_FOLDER}/

    # shellcheck disable=SC2039
    split="${apis:1:${#apis}-2}"
    api_arr=$(echo "${split}" | tr "," "\n")
    for i in ${api_arr}
    do
        value="${i##*:}"
        key="${i%%:*}"

        upFlag=$(isRunning "${value}")

        if [ "${upFlag}" = 0 ]; then
            rm  ${LOG_FOLDER:?}/"${key}".log 2> /dev/null
            touch ${LOG_FOLDER:?}/"${key}".log
        fi
    done
}

get_api_port() {
    echo "${apis}" | tr "," "\n" | grep "$1" | awk -F':' '{print $2}'
}

isRunning() {
    if [ -z "$(lsof -Pi :"$1" -sTCP:LISTEN)" ]; then
        echo 0
    else
        echo 1
    fi
}

isUp() {
    x=1
    # wait max 60 seconds to conclude that an api is up or down
    while [ -z "$(lsof -Pi :"$1" -sTCP:LISTEN)" ] && [  ${x} -le 20 ]; do
        sleep 3;
        x=$(( x + 1 ))
    done

    if [ -z "$(lsof -Pi :"$1" -sTCP:LISTEN)" ]; then
        echo 0
    else
        echo 1
    fi
}

usage() {
    echo "Usage:"
    echo "   \033[31m ./run.sh \033[36mup-all                      \033[33m boots up all infra components, config server and all apis"
    echo "   \033[31m ./run.sh \033[36mup-apis                     \033[33m boots up all apis"
    echo "   \033[31m ./run.sh \033[36mup-infra                    \033[33m boots up all infra components and config server"
    echo "   \033[31m ./run.sh \033[36mup-api        \033[31m[API NAME]    \033[33m boots up given apis"
    echo "   \033[31m ./run.sh \033[36mdown-all                    \033[33m stops all infra components and config server and all apis"
    echo "   \033[31m ./run.sh \033[36mdown-apis                   \033[33m stops all apis"
    echo "   \033[31m ./run.sh \033[36mdown-infra                  \033[33m stops all infra components and config server"
    echo "   \033[31m ./run.sh \033[36mdown-api      \033[31m[API NAME]    \033[33m stops given api"
    echo "   \033[31m ./run.sh \033[36mrestart-api   \033[31m[API NAME]    \033[33m restarts given api"
    echo "   \033[31m ./run.sh \033[36mrestart-infra \033[31m[INFRA NAME]  \033[33m restarts given infra component"
    echo "   \033[31m ./run.sh \033[36mrestart-all                 \033[33m restarts all infra component and all apis"
}

################################
# MAIN
################################

echo "\033[96m****************************************"

start=$(date +%s)

prepare_log_files

case "${1}" in

    ########################
    # BOOT UP
    ########################

    "up-all")
        up_infra
        isDBUp
        sleep 10
        up_apis
        ;;
    "up-infra")
        up_infra
        isDBUp
        ;;
    "up-apis")
        up_apis
        ;;
    "up-api")
        # shellcheck disable=SC2039
        api_name=${*: -1}
        up_api "${api_name}"
        ;;

    ########################
    # BOOT DOWN
    ########################

    "down-all")
        down_apis
        sleep 10
        down_infra
        ;;
    "down-infra")
        down_infra
        ;;
    "down-apis")
        down_apis
        ;;
    "down-api")
        down_api "$2"
        ;;

    ########################
    # RESTART
    ########################

    "restart-api")
        # shellcheck disable=SC2039
        api_name=${*: -1}
        down_api "${api_name}"
        sleep 5
        up_api "${api_name}"
        ;;
    "restart-infra")
        # shellcheck disable=SC2039
        infra_name=${*: -1}
        docker-compose rm -f -s -v "${infra_name}"
        docker-compose up -d "${infra_name}"
        isDBUp
        ;;
    "restart-all")
        down_apis
        sleep 10
        down_infra
        sleep 5
        up_infra
        isDBUp
        up_apis
        ;;
    "-h")
        usage
        ;;
    *)
        echo "\033[31mWARNING! Unknown command: \033[31m\"${1}\""
        echo ""
        usage
esac

end=$(date +%s)

runtime=$((end-start))

echo ""
echo "\033[96m****************************************"
echo "\033[96m[END] Execution completed in \033[31m $runtime seconds "
