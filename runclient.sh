#!/bin/bash
host=localhost
if [ ! -z $1 ]
then
   host=$1
fi
dir=$(cd $(dirname ${BASH_SOURCE[0]}) && pwd)
jarfile=${dir}/build/libs/RmiPerformance.jar
java -cp ${jarfile} \
        com.artechra.RmiTestClient ${host}


