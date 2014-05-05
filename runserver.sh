#!/bin/bash
dir=$(cd $(dirname ${BASH_SOURCE[0]}) && pwd)
jarfile=${dir}/build/libs/RmiPerformance.jar
echo "Using JAR $jarfile for classpath"
# useCodebaseOnly=true is the default from 1.7.0_21 onwards
# http://docs.oracle.com/javase/7/docs/technotes/guides/rmi/enhancements-7.html
java -cp ${jarfile} \
	-Djava.rmi.server.codebase=file://${jarfile} \
	-Djava.rmi.server.useCodebaseOnly=true \
        com.artechra.SimpleRmiServer

