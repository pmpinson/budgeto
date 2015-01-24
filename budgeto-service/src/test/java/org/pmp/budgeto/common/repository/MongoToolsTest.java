package org.pmp.budgeto.common.repository;

import com.mongodb.Mongo;
import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.pmp.budgeto.test.TestTools;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoFactoryBean;
import org.springframework.stereotype.Component;


@RunWith(MockitoJUnitRunner.class)
public class MongoToolsTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @InjectMocks
    private MongoToolsImpl mongoTools;

    @Test
    public void springConf() throws Exception {

        Assertions.assertThat(MongoToolsImpl.class.getAnnotations()).hasSize(1);
        Assertions.assertThat(MongoToolsImpl.class.isAnnotationPresent(Component.class)).isTrue();
    }

    @Test
    public void createMongoFactoryWithNullParam() throws Exception {

        expectedException.expect(NullPointerException.class);

        MongoFactoryBean mongoFactoryBean = mongoTools.mongoFactoryBean(null, 150);

        Assertions.assertThat(mongoFactoryBean).isNotNull();
        Assertions.assertThat(TestTools.getField(mongoFactoryBean, "host", String.class)).isEqualTo("192.168.1.127");
        Assertions.assertThat(TestTools.getField(mongoFactoryBean, "port", String.class)).isNull();
        ;
    }

    @Test
    public void createMongoFactoryBeanNullPort() throws Exception {

        MongoFactoryBean mongoFactoryBean = mongoTools.mongoFactoryBean("192.168.1.127", null);

        Assertions.assertThat(mongoFactoryBean).isNotNull();
        Assertions.assertThat(TestTools.getField(mongoFactoryBean, "host", String.class)).isEqualTo("192.168.1.127");
        Assertions.assertThat(TestTools.getField(mongoFactoryBean, "port", String.class)).isNull();
        ;
    }

    @Test
    public void createMongoFactoryBeanWithPort() throws Exception {

        MongoFactoryBean mongoFactoryBean = mongoTools.mongoFactoryBean("192.168.1.127", 150);

        Assertions.assertThat(mongoFactoryBean).isNotNull();
        Assertions.assertThat(TestTools.getField(mongoFactoryBean, "host", String.class)).isEqualTo("192.168.1.127");
        Assertions.assertThat(TestTools.getField(mongoFactoryBean, "port", Integer.class)).isEqualTo(150);
    }

    @Test
    public void createMongoDbFactoryWithNullUser() throws Exception {

        MongoFactoryBean mongoFactoryBean = Mockito.mock(MongoFactoryBean.class);

        expectedException.expect(NullPointerException.class);

        MongoDbFactory mongoDbFactory = mongoTools.mongoDbFactory(null, null, "mydb", mongoFactoryBean);
    }

    @Test
    public void createMongoDbFactoryWithNullPassword() throws Exception {

        MongoFactoryBean mongoFactoryBean = Mockito.mock(MongoFactoryBean.class);

        expectedException.expect(NullPointerException.class);

        MongoDbFactory mongoDbFactory = mongoTools.mongoDbFactory("user", null, "mydb", mongoFactoryBean);
    }

    @Test
    public void createMongoDbFactoryWithNullDbName() throws Exception {

        MongoFactoryBean mongoFactoryBean = Mockito.mock(MongoFactoryBean.class);

        expectedException.expect(NullPointerException.class);

        MongoDbFactory mongoDbFactory = mongoTools.mongoDbFactory("user", "pass", "mydb", null);
    }

    @Test
    public void createMongoDbFactoryWithNullFactory() throws Exception {

        MongoFactoryBean mongoFactoryBean = Mockito.mock(MongoFactoryBean.class);

        expectedException.expect(NullPointerException.class);

        MongoDbFactory mongoDbFactory = mongoTools.mongoDbFactory("user", "pass", null, null);
    }

    @Test
    public void createMongoDbFactory() throws Exception {

        Mongo mongo = Mockito.mock(Mongo.class);
        MongoFactoryBean mongoFactoryBean = Mockito.mock(MongoFactoryBean.class);
        Mockito.when(mongoFactoryBean.getObject()).thenReturn(mongo);

        MongoDbFactory mongoDbFactory = mongoTools.mongoDbFactory("user", "pass", "mydb", mongoFactoryBean);

        Assertions.assertThat(mongoDbFactory).isNotNull();
        Assertions.assertThat(TestTools.getField(mongoDbFactory, "mongo", Mongo.class)).isEqualTo(mongo);
        Assertions.assertThat(TestTools.getField(mongoDbFactory, "credentials", UserCredentials.class).getUsername()).isEqualTo("user");
        Assertions.assertThat(TestTools.getField(mongoDbFactory, "credentials", UserCredentials.class).getPassword()).isEqualTo("pass");
        Assertions.assertThat(TestTools.getField(mongoDbFactory, "databaseName", String.class)).isEqualTo("mydb");
    }

}
