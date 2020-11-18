#!/bin/bash

#Declaring variables to take CLI args
psql_host=$1
psql_port=$2
db_name=$3
psql_user=$4
psql_password=$5

#Excporting to use as environment variable
export PGPASSWORD=$psql_password

#Saving hostname and cpu num to a variable
lscpu_out=`lscpu`
hostname=$(hostname -f)

#Extracting hardware information to variables
cpu_number=$(echo "$lscpu_out"  | egrep "^CPU\(s\):" | awk '{print $2}' | xargs)
cpu_architecture=$(echo "$lscpu_out" | grep "Architecture:" | awk '{print $2}' | xargs)
cpu_model=$(echo "$lscpu_out" | grep "Model\sname:"| awk -F ": " '{print $2}' | xargs)
cpu_mhz=$(echo "$lscpu_out" | grep "CPU\sMHz:" | awk -F ": " '{print $2}')
l2_cache=$(echo "$lscpu_out" | grep "L2\scache:" | egrep -o "[0-9]{2,}")
total_mem=$(cat /proc/meminfo | grep "MemTotal" | awk '{print $2}')
timestamp=$(date +%F" "%T)

#Inserting extracted data
insertfile="INSERT INTO host_info (hostname, cpu_number, cpu_architecture, cpu_model, cpu_mhz,L2_cache, total_mem, timestamp) VALUES ('$hostname', '$cpu_number', '$cpu_architecture', '$cpu_model', '$cpu_mhz','$l2_cache', '$total_mem', '$timestamp');"

#psql connect and exec
#psql -h $psql_host -U $psql_user -d $db_name -c "$insertfile"
psql -h localhost -p 5432 -U postgres -d host_agent -c "$insertfile"

exit $?
