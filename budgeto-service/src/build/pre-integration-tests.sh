#!/bin/sh
#
# ---------------------------------------------------------------------
# Start mongo db docker container
# ---------------------------------------------------------------------
#

docker run -d --name budgeto-mongo-serveur-autotest -p 27018:27017 mongo
