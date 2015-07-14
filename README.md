An update to test bamboo / jira integration with jira id in commit

# budgeto

project to test some new technos and made some reflexion on architecture.

the project is divided in 2 parts :
* budgeto-api : java rest api to deserve features around persistence storage
* budgeto-ui : web ui to simplify data view and data update

# budgeto-api

## needs
* java 8
* maven 3.2.X
* unix computer
* mongo serveur see [docker](https://github.com/pmpinson/dockerfile), [pre-integration-tests.sh](budgeto-api/src/build/pre-integration-tests.sh) and [post-integration-tests.sh](budgeto-api/src/build/post-integration-tests.sh)

## project configuration
default configuration is in classpath.

there is an extra configuration file available : [etc/config.properties](budgeto-api/etc/config.properties)
for now it's embedded in code but a new external config file will be provided

#### spring section
* spring.profiles.default : *prod* use of [config-prod.properties file](budgeto-api/src/main/resources/config-prod.properties) (default) or *test* usage of [config-test.properties file](budgeto-api/src/main/resources/config-test.properties) (auto use in unit and it tests)

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
* *[start-server.sh](budgeto-api/src/build/start-server.sh)* : run simply the api on a tomcat
* *[start-server-dev.sh](budgeto-api/src/build/start-server-dev.sh)* : run in debug mode available
* *[coverage.sh](budgeto-api/src/build/coverage.sh)* : launch simple site generation with coverage
* *[allsite.sh](budgeto-api/src/build/allsite.sh)* : launch all site with packaging
* *[pre-integration-tests.sh](budgeto-api/src/build/pre-integration-tests.sh)* : do stuff for launch it (mongo container start)
* *[post-integration-tests.sh](budgeto-api/src/build/post-integration-tests.sh)* : launch all site with packaging (mongo container stop)

# budgeto-ui

## needs
* nothing for now

## build info
* nothing for now
