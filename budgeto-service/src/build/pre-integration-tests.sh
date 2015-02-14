#!/bin/sh
#
# ---------------------------------------------------------------------
# Start mongo db docker container
# ---------------------------------------------------------------------
#

rm target/docker.started
rm target/config.properties.sav

echo 'see if budgeto-mongo-testserver container is started'
docker inspect --format '{{ .NetworkSettings.IPAddress }}' budgeto-mongo-testserver

if [ $? -ne 0 ]; then

    echo 'container not started, start it'
    docker run -d --name budgeto-mongo-testserver -p 192.168.59.103:27018:27017 mongo
    touch target/docker.started
fi;

# get configuration
MONGO_ADDRESS=$(docker inspect --format '{{ .NetworkSettings.IPAddress }}' budgeto-mongo-testserver)
MONGO_PORT=27017

echo 'test if mongo available'
for i in `seq 1 20`;
do
        wget --delete-after http://$MONGO_ADDRESS:$MONGO_PORT/
        if [ $? -eq 0 ]; then

                echo 'mongo available update config'
                cp etc/config.properties target/config.properties.sav
                sed -i 's|#mongo.srv.host=?.?.?.?|mongo.srv.host='$MONGO_ADDRESS'|g' etc/config.properties
                sed -i 's|#mongo.srv.port=27017|mongo.srv.port='$MONGO_PORT'|g' etc/config.properties

                exit 0
        fi;
        echo 'retry'
        sleep 3
done

echo 'mongo not available'
sh src/build/post-integration-tests.sh
exit 1
