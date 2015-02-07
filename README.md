# budgeto-parent

project to test some new technos and made some reflexion on architecture.

the project is divided in 2 parts :
* budgeto-service : java rest api to deserve features around persistence storage
* budgeto-web : web ui to simplify data view and data update

# budgeto-service

## needs
* java 8
* maven 3.2.X
* unix computer
* mongo serveur see [docker](https://github.com/pmpinson/dockerfile), [pre-integration-tests.sh](budgeto-service/src/build/pre-integration-tests.sh) and [post-integration-tests.sh](budgeto-service/src/build/post-integration-tests.sh)

## project configuration
default configuration is in classpath.

there is an extra configuration file available : [etc/config.properties](budgeto-service/etc/config.properties)
for now it's embedded in code but a new external config file will be provided

#### spring section
* spring.profiles.default : *prod* use of [config-prod.properties file](budgeto-service/src/main/resources/config-prod.properties) (default) or *test* usage of [config-test.properties file](budgeto-service/src/main/resources/config-test.properties) (auto use in unit and it tests)

#### mongo section
* mongo.srv.host : ip or name of host
* mongo.srv.port : port of host
* mongo.db.name : name of db to use
* mongo.db.user : user
* mongo.db.password : login

## build info
#### maven command
use default maven lifecycle :
* test
* verify (launch and stop a mongo db container)
* use sonar : sonar after a mvn clean verify to load quality analysis


#### sh utils
* *[start-server.sh](budgeto-service/src/build/start-server.sh)* : run simply the api on a tomcat
* *[start-server-dev.sh](budgeto-service/src/build/start-server-dev.sh)* : run in debug mode available
* *[coverage.sh](budgeto-service/src/build/coverage.sh)* : launch simple site generation with coverage
* *[allsite.sh](budgeto-service/src/build/allsite.sh)* : launch all site with packaging
* *[pre-integration-tests.sh](budgeto-service/src/build/pre-integration-tests.sh)* : do stuff for launch it (mongo container start)
* *[post-integration-tests.sh](budgeto-service/src/build/post-integration-tests.sh)* : launch all site with packaging (mongo container stop)

# budgeto-web

## needs
* nothing for now

## build info
* nothing for now
