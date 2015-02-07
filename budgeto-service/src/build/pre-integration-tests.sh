#!/bin/sh
#
# ---------------------------------------------------------------------
# Start mongo db docker container
# ---------------------------------------------------------------------
#

docker run -d --name budgeto-mongo-server-autotest -p 27018:27017 mongo
MONGO_ADDRESS=$(docker inspect --format '{{ .NetworkSettings.IPAddress }}' budgeto-mongo-server-autotest)
sleep 10
sed -i 's|#mongo.srv.host=?.?.?.?|mongo.srv.host='$MONGO_ADDRESS'|g' etc/config.properties
sed -i 's|#mongo.srv.port=27017|mongo.srv.port=27017|g' etc/config.properties
