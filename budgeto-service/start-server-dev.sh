#!/bin/sh
#
# ---------------------------------------------------------------------
# Start application in tomcat8 (port 9001) with cargo. run with jvm debug mode on port 8998m not wait
# ---------------------------------------------------------------------
#

mvn clean package cargo:run -Pcargo-dev -Dmaven.test.skip=true
