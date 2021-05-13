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

TEST_KAFKA_NAME='hexagonaldemo_test_kafka'
TEST_KAFKA_ENVS='--env KAFKA_BROKER_ID=1 --env KAFKA_ZOOKEEPER_CONNECT='hexagonaldemo_test_zookeeper:2181' --env KAFKA_ADVERTISED_LISTENERS='PLAINTEXT://localhost:9092' --env KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR=1 --env KAFKA_GROUP_INITIAL_REBALANCE_DELAY_MS=0 --env KAFKA_AUTO_CREATE_TOPICS_ENABLE=true'
TEST_KAFKA_DATA='hexagonaldemo_test_kafka_data'

TEST_ZOOKEEPER_NAME='hexagonaldemo_test_zookeeper'
TEST_ZOOKEEPER_ENVS='--env ZOOKEEPER_CLIENT_PORT=2181 --env ZOOKEEPER_TICK_TIME=2000'

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
    while [[ $(docker logs $TEST_DB_NAME 2>/dev/null --tail 100 ) != *"ready for connections"* ]] && [  ${x} -le 30 ]; do
        sleep 1;
        x=$(( x + 1 ))
        printf "."
    done

    if [[ $(docker logs $TEST_DB_NAME 2>/dev/null --tail 100 ) != *"ready for connections"* ]]; then
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
# KAFKA
#####################################
up_kafka() {
    if [[ ! $(isUp 9092) ]]; then
        echo ">> starting: up kafka"
        # shellcheck disable=SC2086
        create_volume $TEST_KAFKA_DATA
        docker run --network ${TEST_NETWORK} --name ${TEST_KAFKA_NAME} -v ${TEST_KAFKA_DATA} -m 512m -p 9092:9092 -d ${TEST_KAFKA_ENVS} confluentinc/cp-kafka
    else
        echo ">> kafka is already up"
    fi
}

down_kafka() {
    if [[ ! $(isDown 9092) ]]; then
        echo ">> down kafka"
        docker rm -f ${TEST_KAFKA_NAME}
        docker volume rm ${TEST_KAFKA_DATA}
    else
        echo ">> kafka is already down"
    fi
}

up_zookeeper() {
    if [[ ! $(isUp 2181) ]]; then
        echo ">> up zookeeper"
        # shellcheck disable=SC2086
        docker run --network ${TEST_NETWORK} --name ${TEST_ZOOKEEPER_NAME} -m 512m -p 2181:2181 -d ${TEST_ZOOKEEPER_ENVS} confluentinc/cp-zookeeper
    else
        echo ">> zookeeper is already up"
    fi

}

down_zookeeper() {
    if [[ ! $(isDown 2181) ]]; then
        echo ">> down zookeeper"
        docker rm -f ${TEST_ZOOKEEPER_NAME}
    else
        echo ">> zookeeper is already down"
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
    "kafka")
        case "${2}" in
        "up")
            echo Starting zookeeper && up_zookeeper;
            echo Starting kafka && up_kafka;
            ;;
        "down")
            echo Stopping zookeeper && down_zookeeper;
            echo Stopping kafka && down_kafka;
            ;;
        esac;;
    "infra")
        case "${2}" in
        "up")
            echo Starting db && up_db;
            echo Starting redis && up_redis;
            echo Starting zookeeper && up_zookeeper;
            echo Starting kafka && up_kafka;
            ;;
        "down")
            echo Stopping db && down_db;
            echo Stopping redis && down_redis;
            echo Stopping zookeeper && down_zookeeper;
            echo Stopping kafka && down_kafka;
            ;;
        esac
        ;;
    *)
        echo Unknown command! "${1}"
esac
