#!/bin/sh
host=localhost
if [ ! -z $1 ]
then
   host=$1
fi
dir=$(cd $(dirname ${BASH_SOURCE[0]}) && pwd)
jarfile=${dir}/build/libs/RmiPerformance.jar
java -cp ${jarfile} \
        -Djavax.net.ssl.keyStore=clientkeystore \
        -Djavax.net.ssl.keyStorePassword=changeit \
        -Djavax.net.ssl.trustStore=clientkeystore \
        -Djavax.net.ssl.trustStorePassword=changeit \
        com.artechra.RmiSslTestClient ${host}


