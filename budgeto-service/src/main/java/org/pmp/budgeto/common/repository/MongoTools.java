package org.pmp.budgeto.common.repository;

import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoFactoryBean;

/**
 * common to help configuration of a mongo db
 */
public interface MongoTools {

    /**
     * create a MongoFactoryBean
     *
     * @param host the adresse of host to search for mongo serveur
     * @param port the port of host to search for mongo serveur (not mandatory)
     * @return factory
     * @throws Exception
     */
    MongoFactoryBean mongoFactoryBean(String host, Integer port) throws Exception;

    /**
     * create the mongo db factory
     *
     * @param user             the user name
     * @param pass             the password
     * @param dbName           the name of bdd
     * @param mongoFactoryBean the mongo factory server
     * @return the db factory
     * @throws Exception
     */
    MongoDbFactory mongoDbFactory(String user, String pass, String dbName, MongoFactoryBean mongoFactoryBean) throws Exception;

}
