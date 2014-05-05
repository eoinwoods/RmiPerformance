#!/bin/sh
dir=$(cd $(dirname ${BASH_SOURCE[0]}) && pwd)
jarfile=${dir}/build/libs/RmiPerformance.jar
echo "Using JAR $jarfile for classpath"

clientAuthFlag=${1:-false}

# useCodebaseOnly=true is the default from 1.7.0_21 onwards
# http://docs.oracle.com/javase/7/docs/technotes/guides/rmi/enhancements-7.html
#        -Djavax.net.debug=ssl \
java -cp ${jarfile} \
        -Djava.protocol.handler.pkgs=com.sun.net.ssl.internal.www.protocol \
	    -Djava.rmi.server.codebase=file://${jarfile} \
	    -Djava.rmi.server.useCodebaseOnly=true \
        -Djavax.net.ssl.keyStore=serverkeystore \
        -Djavax.net.ssl.keyStorePassword=changeit \
        -Djavax.net.ssl.trustStore=serverkeystore \
        -Djavax.net.ssl.keyStorePassword=changeit \
        -DrequireClientAuth=${clientAuthFlag} \
        com.artechra.SimpleSslRmiServer

