#!/bin/sh
#
# ---------------------------------------------------------------------
# Start mongo db docker container
# ---------------------------------------------------------------------
#

# start
docker run -d --name budgeto-mongo-server-autotest -p 27018:27017 mongo
MONGO_ADDRESS=$(docker inspect --format '{{ .NetworkSettings.IPAddress }}' budgeto-mongo-server-autotest)
MONGO_PORT=27017

# update confi ip / port
sed -i 's|#mongo.srv.host=?.?.?.?|mongo.srv.host='$MONGO_ADDRESS'|g' etc/config.properties
sed -i 's|#mongo.srv.port=27017|mongo.srv.port='$MONGO_PORT'|g' etc/config.properties

# wait mongo ready
for i in `seq 1 20`;
do
        if curl http://$MONGO_ADDRESS:$MONGO_PORT/ ; then
                exit 0
        fi;
        sleep 3
done
exit 1
echo "$(date) - end"
