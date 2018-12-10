* Execution
In order to run application you need to issue command:

java -jar bedkowski.jar

* Logging
By default all loging is disabled. For viewing messages exchanged
you can optionally set log level:
java -Dlog_level=info -jar bedkowski.jar

* Dependencies
** Whole server is based on netty.io
** For graph building I used JGraphT
** log4j2 as logging framework

* Testing machine
Macbook Air 13' 2017, Mac OS (10.13.6) High Sierra, 1,8 GHz Intel Core i5, 8GB RAM

* Java Version
openjdk version "11.0.1" 2018-10-16
OpenJDK Runtime Environment 18.9 (build 11.0.1+13)
OpenJDK 64-Bit Server VM 18.9 (build 11.0.1+13, mixed mode)

* Source code was cross-compiled for java 1.8 and assembled using maven task runner:
Java version: 11.0.1, vendor: Oracle Corporation, runtime: /Library/Java/JavaVirtualMachines/openjdk-11.0.1.jdk/Contents/Home
Default locale: pl_PL, platform encoding: UTF-8
OS name: "mac os x", version: "10.13.6", arch: "x86_64", family: "mac"

* Results discussion
** Phase1 - pass
** Phase2 - pass
** Phase3 - pass
** Phase4 - pass
** Phase5 - pass
** Phase6 - occiasional fail
*** Things verified
 a) Synchronized locks of node/edge operations manager
 b) ReadWrite locking of node/edge operations manager
 c) StampedLock with optimistic locking of node/edge operations manager (final implementation)
 d) LinkedBlockingQueue in order to run received operations sequentially based on arrival time
 e) Introducing artificial delay of 300ms using Thread.sleep() before sending response
 f) Blocking execution thread until confirmation from TCP socket is received

** Thought of 3 main reasons why this may happen:
*** Concurrent read/writes - excluded with least performant synchronized locking
*** Error in JGraphT implementation for distance based graph search - highly unlikely as this is
   oss library with very thorough unit tests
*** In-transit scenarios which cause modifications of graph before responding to the client

* SUMMARY
In order to fully validate these hypotheses I'd record whole communication with
test client and replay it using unit tests with proper error messages received during
given session which falls out of scope this exercise so I'm providing it the way it is right now.

You can find multiple implementations of synchronisation strategies used in:

org.collibra.challenge.graph.manager.synchronisation

package.

BlockingQueue was the last one verifidied and since it didn't prove anything I decided to remove it.


