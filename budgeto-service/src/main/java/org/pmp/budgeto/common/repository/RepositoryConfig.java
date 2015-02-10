package org.pmp.budgeto.common.repository;

import org.apache.commons.lang3.Validate;
import org.pmp.budgeto.common.config.ConfigException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoFactoryBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.WriteConcernResolver;

/**
 * config the repository components for inject
 * scan on org.pmp.budgeto.common.repository package and load mongo repositories from
 * configure the database from mongo.srv.host, mongo.srv.port (not mandatory), mongo.db.name, mongo.db.user, mongo.db.password from Environment
 */
@Configuration
@ComponentScan(basePackages = "org.pmp.budgeto.common.repository")
public class RepositoryConfig {

    private static final String PROP_SRV_BASE = "mongo.srv";
    public static final String PROP_SRV_HOST = PROP_SRV_BASE + ".host";
    public static final String PROP_SRV_PORT = PROP_SRV_BASE + ".port";
    private static final String PROP_DB_BASE = "mongo.db";
    public static final String PROP_DB_USER = PROP_DB_BASE + ".user";
    public static final String PROP_DB_PASSWORD = PROP_DB_BASE + ".password";
    public static final String PROP_DB_NAME = PROP_DB_BASE + ".name";

    @Autowired
    private WriteConcernResolver writeConcernResolver;

    @Autowired
    private Environment environment;

    @Autowired
    private MongoTools mongoTools;

    @Bean
    public MongoTemplate mongoTemplate() throws ConfigException {
        final MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory());
        mongoTemplate.setWriteConcernResolver(writeConcernResolver);
        return mongoTemplate;
    }

    @Bean
    public MongoFactoryBean mongoFactoryBean() {
        String srvHost = Validate.notNull(environment.getProperty(PROP_SRV_HOST));
        String portValue = environment.getProperty(PROP_SRV_PORT);
        Integer srvPort = portValue == null ? null : Integer.valueOf(portValue);

        return mongoTools.mongoFactoryBean(srvHost, srvPort);
    }

    @Bean
    public MongoDbFactory mongoDbFactory() throws ConfigException {
        String dbUser = Validate.notNull(environment.getProperty(PROP_DB_USER));
        String dbPass = Validate.notNull(environment.getProperty(PROP_DB_PASSWORD));
        String dbName = Validate.notNull(environment.getProperty(PROP_DB_NAME));

        return mongoTools.mongoDbFactory(dbUser, dbPass, dbName, mongoFactoryBean());
    }

//    @Bean
//    public MongoListener listener() {
//        return new MongoListener();
//    }

}
