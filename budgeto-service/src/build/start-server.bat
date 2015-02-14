rem
rem ---------------------------------------------------------------------
rem Start application in tomcat8 (port 9001) with cargo
rem ---------------------------------------------------------------------
rem

mvn clean package cargo:run -Dmaven.test.skip=true
