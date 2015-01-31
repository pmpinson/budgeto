# budgeto-parent

project to test some new technos and made some reflexion on architecture.

the project is divided in 2 parts :
* budgeto-service : java rest api to deserve features around persistence storage
* budgeto-web : web ui to simplify data view and data update

# budgeto-service

## needs
* java 8
* maven 3.2.X
* mongo serveur

`docker run --name budgeto-mongo-serveur-test -p 27018:27017 -d mongo`

`docker run -it --link budgeto-mongo-serveur-test:mongo --rm mongo sh -c 'exec mongo "$MONGO_PORT_27017_TCP_ADDR:$MONGO_PORT_27017_TCP_PORT/budgeto-test"'`

## project configuration
for now it's embedded in code but a new external config file will be provided

#### mongo section
* mongo.srv.host : ip or name of host
* mongo.srv.port : port of host
* mongo.db.name : name of db to use
* mongo.db.user : user
* mongo.db.password : login

## build info
#### maven command
use default maven lifecycle
integration test map to verify

#### sh utils
* run simply the api on a tomcat : use start-server.sh
* run in debug mode available : use start-server-dev.sh
* launch simple site generation with coverage : use coverage.sh
* launch all site with packaging : allsite.sh 

# budgeto-web

## needs
* nothing for now

## build info
* nothing for now
