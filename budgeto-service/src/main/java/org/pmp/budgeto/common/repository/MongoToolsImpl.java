package org.pmp.budgeto.common.repository;

import org.apache.commons.lang3.Validate;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoFactoryBean;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.stereotype.Component;

/**
 * implementations of MongoTools
 */
@Component
public class MongoToolsImpl implements MongoTools {

    @Override
    public MongoFactoryBean mongoFactoryBean(String host, Integer port) throws Exception {
        Validate.notNull(host);

        MongoFactoryBean mongoFactoryBean = new MongoFactoryBean();
        mongoFactoryBean.setHost(host);
        if (port != null) {
            mongoFactoryBean.setPort(port);
        }
        return mongoFactoryBean;
    }

    @Override
    public MongoDbFactory mongoDbFactory(String user, String pass, String dbName, MongoFactoryBean mongoFactoryBean) throws Exception {
        Validate.notNull(user);
        Validate.notNull(pass);
        Validate.notNull(dbName);
        Validate.notNull(mongoFactoryBean);

        UserCredentials userCredentials = new UserCredentials(user, pass);
        return new SimpleMongoDbFactory(mongoFactoryBean.getObject(), dbName, userCredentials);
    }

}
