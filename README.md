# emender-jenkins

REST API and web-based interface between Jenkins, Jenkins jobs and Emender test suite.


## Prerequisities

### Prerequisities for compiling and installation

1. JVM version 6 or better
1. Leiningen

### For the already compiled service

1. JRE version 6 or better (JRE 7 is recommended)



## Installation

1. Clone the emender-jenkins repository
1. Run the following commands:

    $ lein deps
    $ lein uberjar

Result is stored in an Java archive (JAR) that can be found in the 'target/uberjar' subdirectory.


## Usage

To start the service, run the following command:

    $ lein run

Or alternatively you can use the Java archive:

    $ java -jar target/uberjar/emender-jenkins-0.1.0-SNAPSHOT-standalone.jar

## Options

Current version of this service accepts the following command line options:

## REST API

Please read [this file](doc/REST_API.adoc)

## License

Copyright © 2016 Pavel Tisnovsky, Red Hat Inc.

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.

