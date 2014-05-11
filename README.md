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

        $ gradle dist

* You'll find the file `rmi-perf.tar`  in the `build/distributions` directory.  This contains everything you need to run the tests.
* Copy the .tar file anywhere you want to run the clients or servers.
* Use the `runclient.sh` and `runserver.sh` scripts to run a plain JRMP RMI test (this pair use 1099 for the registry port)
* Use the `runsslclient.sh` and `runsslserver.sh` scripts to run a JRMP over SSL RMI test (this pair use 2099 for the registry port)

Results
-------

So what were the results?  Well, briefly, using Amazon EC2 instances to test RMI calls between them, I got the following results:

* 10,000 addition calls takes 5700 msec over JRMP and 10,000 msec over SSL
* 10,000 concatenation calls take 6000 msec over JRMP and 8000 msec over SSL
* Service lookup took about 350 msec over JRMP and 2000 msec over SSL

These are averaged results over three runs.  Ping time between EC2 instances is about 0.5 msec.

What I look away from this was that SSL does add a definite overhead, in some cases almost doubling the time taken to make a simple RMI call.  If you make a very large number of RMI calls on critical transaction paths, this needs to be taken into account.  On the other hand, all the simple JRMP calls take (broadly) 500 microseconds and doubling this is still only a millisecond.  So unless your services are very fast, this is unlikely to be the most pressing problem you have.

