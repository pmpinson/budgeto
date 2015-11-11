rem
rem ---------------------------------------------------------------------
rem Start application in tomcat8 (port 9001) with cargo. run with jvm debug mode on port 8998m not wait
rem ---------------------------------------------------------------------
rem

mvn package cargo:run -Pcargo-dev -Dmaven.test.skip=true
