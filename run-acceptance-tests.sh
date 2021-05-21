#!/usr/bin/env bash


# Print the commands as it is executed. Useful for debugging
set -x

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
        printf "${api_name} cannot be boot up in %s seconds, quited." "$x"
        exit 1
    else
        printf "${api_name} becomes up and running in %s seconds\n" "$x"
    fi
}

run_sql() {
    file_name=$1
    docker exec -i "$(docker ps -a | grep mysql | awk '{print $1}')" mysql -uroot -p"password" mysql < "docs/ddl/${file_name}.sql"
}

bootup() {
    echo " :> Bootup starts"

    ./gradlew :payment-api:bootRun -PskipInfraSetup --stacktrace >payment-api.log  &

    echo " :> Payment api boot is triggered"

    ./gradlew :ticket-api:bootRun -PskipInfraSetup --stacktrace >ticket-api.log &

    echo " :> Ticket api boot is triggered"

    is_api_up "payment-api"

    echo " :> Payment api is started"

    is_api_up "ticket-api"

    echo " :> Ticket api is started"
}

complete() {
    # shellcheck disable=SC2046
    # shellcheck disable=SC2009
    kill -9 $(ps aux | grep gradlew | grep boot | awk '{print $2}')

    echo " :> Api instances are killed"

    rm payment-api.log 2> /dev/null
    rm ticket-api.log 2> /dev/null

    echo " :> Temp files are deleted"
}

local-setup() {
    export DB_PORT=3306
    sh infra-setup.sh infra up
    run_sql 0_initialize
    run_sql 1_schema
    run_sql 2_data

    echo " :> Infra is up and ready for acceptance tests at local"
}

#####################################
# MAIN
#####################################

case "${1}" in
    "ci")
        bootup;;
    "start")
        complete
        bootup;;
    "stop")
        complete;;
    "local")
        complete
        local-setup
        bootup;;
    *)
        echo Unknown command! "${1}"
esac