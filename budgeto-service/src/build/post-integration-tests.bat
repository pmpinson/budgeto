rem
rem ---------------------------------------------------------------------
rem Stop mongo db docker container
rem ---------------------------------------------------------------------
rem

if [ -f target/docker.started ]; then
    echo 'stop started mongo'
    docker stop budgeto-mongo-testserver && docker rm -v budgeto-mongo-testserver
fi

if [ -f target/config.properties.sav ]; then
    echo  'clean config files'
    rm etc/config.properties
    mv target/config.properties.sav etc/config.properties
fi
