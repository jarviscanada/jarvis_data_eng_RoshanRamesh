#!/bin/bash

#Variable definition given below
db_username = $1
db_password = $2
db_task = $3

#Docker container status check and start if docker is not running
systemctl status docker || systemctl start docker

#Exporting suggested password for user (User - postgres)
export PGPASSWORD='password'

#docker tasks - Create, error display, start and stop (code below) using if clauses
if [[ $task = "create" ]];
  then
    if [[ $(docker container ls -a -f name=jrvs-psql | wc -l) -eq 2 ]]; then
        echo "Container already exists - ERRRRRRR"
        exit 1
    fi

  #check if username and password are passed as args
    if [[ $db_username ]] || [[ $db_password ]]; then
        echo "Please pass username and password args via CLI"
        exit 1
    fi

  #Creatin pgdata
    sudo docker volume create pgdata
    docker run --name jrvs-psql -e POSTGRES_PASSWORD=${db_password} -e POSTGRES_USER=${db_username} -d -v pgdata:/var/lib/postgresql/data -p 5432:5432 $db_username
    exit $?
  fi

#check if container is created
if [[ ! $(docker ps -a | grep "jrvs-psql") ]]; then
    echo "Container does not exist- please create"
    exit 1
fi

#START and STOP containeif [[ $task = "start" ]]; then
    docker container stop jrvs-psql
    exit 0
fi

if [[ $task = "stop" ]]; then
    docker container stop jrvs-psql
    exit 0
fi

