#!/bin/sh
#
# ---------------------------------------------------------------------
# Start mongo db docker container
# ---------------------------------------------------------------------
#

docker stop budgeto-mongo-server-autotest && docker rm -v budgeto-mongo-server-autotest
