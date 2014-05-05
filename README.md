RmiPerformance
==============

A simple test harness to allow the overhead of RMI over SSL to be tested.

Ever wondered how fast based JRMI RMI is?  Or what the overhead of securing it with SSL would be?  
I was recently in the position of wanting to work this out and couldn't find anything via Google that told me.  
So I decided to write some simple code to test it.  And here it is.

Basic Usage
-----------

* Prerequisites to build the code are just Java 7 and Gradle.
* The assumption is that you're running the code on a system with bash(1), tar(1) and a JDK installed
* Build the code:

        $ gradle archive

* You'll find the file `rmi-perf.tar`  in the `build/distributions` directory.  This contains everything you need to run the tests.
* Copy the .tar file anywhere you want to run the clients or servers.
* Use the `runclient.sh` and `runserver.sh` scripts to run a plain JRMP RMI test (this pair use 1099 for the registry port)
* Use the `runsslclient.sh` and `runsslserver.sh` scripts to run a JRMP over SSL RMI test (this pair use 2099 for the registry port)


