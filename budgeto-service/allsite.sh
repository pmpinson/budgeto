#!/bin/sh
#
# ---------------------------------------------------------------------
# Run unit tests, integration tests and generate all site
# ---------------------------------------------------------------------
#

mvn clean verify site -Pdeploy
