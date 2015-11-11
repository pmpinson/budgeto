#!/bin/sh
#
# ---------------------------------------------------------------------
# Run unit tests, integration tests and generate all site
# ---------------------------------------------------------------------
#

mvn verify site -Pdeploy
