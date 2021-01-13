#!/usr/bin/env bash

# NETWORK
TEST_NETWORK='hexagonaldemo_network'

# DB
TEST_DB_DATA='hexagonaldemo_test_db_data'
TEST_DB_NAME='hexagonaldemo_test_db'
TEST_DB_IMAGE='mysql'
TEST_DB_PASSWORD='password'

TEST_REDIS_SERVER_NAME='hexagonaldemo_test_redis'
TEST_REDIS_IMAGE='bitnami/redis:latest'
TEST_REDIS_ENVS='REDIS_PASSWORD=redisPassword'
TEST_REDIS_PORT=6379

#####################################
# DOCKER
#####################################

isUp() {
    # shellcheck disable=SC2086
    if [[ -n $(lsof -Pi :$1) ]]; then
        echo 1
    fi
}

isDown() {
    # shellcheck disable=SC2086
    if [[ -z $(lsof -Pi :$1) ]]; then
        echo 1
    fi
}

create_volume(){
    if ! (docker volume ls | grep "${1}");
    then
       echo ">> Creating ${1} volume"
       docker volume create "${1}"
    fi
}

create_network(){
    if ! (docker network ls | grep "${1}");
    then
       echo ">> Creating ${1} network"
       docker network create "${1}"
    fi
}

#####################################
# DATABASE
#####################################

isDBUp() {
    x=1
    while [[ $(docker logs $TEST_DB_NAME 2>/dev/null --tail 100 ) != *"Ready for start up"* ]] && [  ${x} -le 30 ]; do
        sleep 1;
        x=$(( x + 1 ))
        printf "."
    done

    if [[ $(docker logs $TEST_DB_NAME 2>/dev/null --tail 100 ) != *"Ready for start up"* ]]; then
        printf "DB cannot be boot up in %s seconds, quited." "$x"
        exit 1
    else
        printf "DB becomes up and running in %s seconds\n" "$x"
    fi
}

up_db() {
    if [[ ! $(isUp 4306) ]]; then
        echo ">> starting: db up"
        create_volume $TEST_DB_DATA
        create_network $TEST_NETWORK
        docker run --rm --network ${TEST_NETWORK} --name ${TEST_DB_NAME} --mount source=${TEST_DB_DATA},target=/var/lib/mysql --env MYSQL_ROOT_PASSWORD=${TEST_DB_PASSWORD} -p 4306:3306 -d ${TEST_DB_IMAGE}
        isDBUp
    else
        echo ">> db is already up"
    fi
}

down_db() {
    if [[ ! $(isDown 4306) ]]; then
        echo ">> down db"
        docker rm -v -f ${TEST_DB_NAME}
        docker volume rm ${TEST_DB_DATA}
    else
        echo ">> db is already down"
    fi
}

#####################################
# REDIS
#####################################

up_redis() {
  if [[ ! $(isUp $TEST_REDIS_PORT) ]]; then
    echo ">> starting: up redis"
    docker run -d --name ${TEST_REDIS_SERVER_NAME} -e "${TEST_REDIS_ENVS}" -p 6379:6379 --network ${TEST_NETWORK} ${TEST_REDIS_IMAGE}
  else
    echo ">> redis is already up"
  fi
}

down_redis() {
  if [[ ! $(isDown $TEST_REDIS_PORT) ]]; then
    echo ">> down redis"
    docker rm -f ${TEST_REDIS_SERVER_NAME}
  else
    echo ">> redis is already down"
  fi
}

#####################################
# MAIN
#####################################

case "${1}" in
    "db")
        case "${2}" in
        "up") echo Starting db && up_db;;
        "down") echo Stopping db && down_db;;
        esac;;
    "redis")
        case "${2}" in
        "up") echo Starting redis && up_redis;;
        "down") echo Stopping redis && down_redis;;
        esac;;
    "infra")
        case "${2}" in
        "up")
            echo Starting db && up_db;
            echo Starting redis && up_redis;;
        "down")
            echo Stopping db && down_db;
            echo Stopping redis && down_redis;;
        esac;;
    *)
        echo Unknown command! "${1}"
esac
