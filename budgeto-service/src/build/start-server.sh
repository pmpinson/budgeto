#!/bin/sh
#
# ---------------------------------------------------------------------
# Start application in tomcat8 (port 9001) with cargo
# ---------------------------------------------------------------------
#

mvn clean package cargo:run -Dmaven.test.skip=true
