#!/usr/bin/env bash

######################################
# RUN ACCEPTANCE TESTS
######################################

is_db_up() {
    mysql_id=$(docker ps -a | grep  modular-architecture-hexagonal-demo-project_mysql | awk {'print $1'})
    x=1
    logs=$(docker container logs 2>/dev/null --tail 100 "$mysql_id")
    # shellcheck disable=SC2039
    while [[ $logs != *"started."* ]] && [  ${x} -le 30 ]; do
        sleep 1;
        x=$(( x + 1 ))
        printf "."
        logs=$(docker container logs 2>/dev/null --tail 100 "$mysql_id")
    done

    logs=$(docker container logs 2>/dev/null --tail 100 "$mysql_id")
    # shellcheck disable=SC2039
    if [[ $logs != *"started."* ]]; then
        printf " :> DB cannot be boot up in %s seconds, quited." "$x"
        echo " :> \nLOGS:"
        docker container logs 2>/dev/null --tail 100 "$mysql_id"
        exit 1
    else
        printf " :> DB becomes up and running in %s seconds\n" "$x"
    fi
}

is_api_up() {
    echo " :> $1 monitoring started"
    api_name=$1
    x=1
    while [[ $( cat "${api_name}.log" ) != *"Tomcat started"* ]] && [  ${x} -le 30 ]; do
        sleep 1;
        x=$(( x + 1 ))
        printf "."
    done

    if [[ $( cat "${api_name}.log" ) != *"Tomcat started"* ]]; then
        printf " :> ${api_name} cannot be boot up in %s seconds, quited." "$x"
        exit 1
    else
        printf " :> ${api_name} becomes up and running in %s seconds\n" "$x"
    fi
}

start_apis() {
    echo " :> Api bootup is triggered"
    ./gradlew clean :payment-api:build -x test :payment-api:bootRun -PskipInfraSetup --stacktrace >payment-api.log  &
    sleep 3
    echo " :> Payment api boot is triggered"

    ./gradlew clean :ticket-api:build -x test :ticket-api:bootRun -PskipInfraSetup --stacktrace >ticket-api.log &
    sleep 3
    echo " :> Ticket api boot is triggered"

    is_api_up "payment-api"
    echo " :> Payment api is started"

    is_api_up "ticket-api"
    echo " :> Ticket api is started"
}

stop_infra() {
    docker compose  -f docker-compose.yml down
    echo " :> Infra is stopped at local"
}

stop_apis() {
    # shellcheck disable=SC2046
    # shellcheck disable=SC2009
    pid_list=$(ps aux | grep gradlew | grep boot | awk '{print $2}')
    if [ -n "$pid_list" ]; then

        # shellcheck disable=SC2046
        # shellcheck disable=SC2116
        # shellcheck disable=SC2069
        kill -9 $(echo "$pid_list") >/dev/null 2>&1

        echo " :> Api instances are killed"

        rm payment-api.log 2> /dev/null
        rm ticket-api.log 2> /dev/null

        echo " :> Temp files are deleted"
    fi
}

start_infra() {
    docker compose -f docker-compose.yml up -d
    echo " :> Infra is initializing"
    sleep 5
    is_db_up
    echo " :> Infra is up and ready for acceptance tests at local"
}

test() {
    echo " :> Acceptance test is going to start"
    ./gradlew :acceptance-test-suite:clean :acceptance-test-suite:cucumber -PskipInfraSetup -Dorg.gradle.daemon=false
    echo " :> Acceptance test ended"
}

#####################################
# MAIN
#####################################

case "${1}" in
    "start_apis")
        start_apis;;
    "start_infra")
        start_infra;;
    "start")
        start_infra
        start_apis;;
    "stop_apis")
        stop_apis;;
    "stop_infra")
        stop_infra;;
    "stop")
        stop_infra
        stop_apis;;
    "restart_infra")
        stop_infra
        start_infra;;
    "restart_apis")
        stop_apis
        start_apis;;
    "restart")
        stop_apis
        stop_infra
        start_infra
        start_apis;;
    "test")
        test;;
    "run")
        stop_apis
        stop_infra
        start_infra
        start_apis
        test
        stop_apis
        stop_infra
        ;;
    *)
        echo Unknown command! "${1}"
esac