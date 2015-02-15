package org.pmp.budgeto.common.repository;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.pmp.budgeto.test.TestTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoFactoryBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.WriteConcernResolver;


@RunWith(MockitoJUnitRunner.class)
public class RepositoryConfigTest {

    @Mock
    private MongoTools mongoTools;

    @Mock
    private Environment environment;

    @Mock
    private WriteConcernResolver writeConcernResolver;

    @InjectMocks
    private RepositoryConfig repositoryConfig;

    @Test
    public void springConf() throws Exception {

        Assertions.assertThat(RepositoryConfig.class.getAnnotations()).hasSize(2);
        Assertions.assertThat(RepositoryConfig.class.isAnnotationPresent(Configuration.class)).isTrue();
        Assertions.assertThat(RepositoryConfig.class.isAnnotationPresent(ComponentScan.class)).isTrue();
        Assertions.assertThat(RepositoryConfig.class.getAnnotation(ComponentScan.class).basePackages()).containsExactly("org.pmp.budgeto.common.repository");

        Assertions.assertThat(RepositoryConfig.class.getDeclaredField("environment").getAnnotations()).hasSize(1);
        Assertions.assertThat(RepositoryConfig.class.getDeclaredField("environment").isAnnotationPresent(Autowired.class)).isTrue();
        Assertions.assertThat(RepositoryConfig.class.getDeclaredField("writeConcernResolver").getAnnotations()).hasSize(1);
        Assertions.assertThat(RepositoryConfig.class.getDeclaredField("writeConcernResolver").isAnnotationPresent(Autowired.class)).isTrue();
        Assertions.assertThat(RepositoryConfig.class.getDeclaredField("mongoTools").getAnnotations()).hasSize(1);
        Assertions.assertThat(RepositoryConfig.class.getDeclaredField("mongoTools").isAnnotationPresent(Autowired.class)).isTrue();

        Assertions.assertThat(RepositoryConfig.class.getDeclaredMethod("mongoTemplate", new Class[]{}).getAnnotations()).hasSize(1);
        Assertions.assertThat(RepositoryConfig.class.getDeclaredMethod("mongoTemplate", new Class[]{}).getAnnotation(Bean.class)).isNotNull();
        Assertions.assertThat(RepositoryConfig.class.getDeclaredMethod("mongoFactoryBean", new Class[]{}).getAnnotations()).hasSize(1);
        Assertions.assertThat(RepositoryConfig.class.getDeclaredMethod("mongoFactoryBean", new Class[]{}).getAnnotation(Bean.class)).isNotNull();
        Assertions.assertThat(RepositoryConfig.class.getDeclaredMethod("mongoDbFactory", new Class[]{}).getAnnotations()).hasSize(1);
        Assertions.assertThat(RepositoryConfig.class.getDeclaredMethod("mongoDbFactory", new Class[]{}).getAnnotation(Bean.class)).isNotNull();
    }

    @Test
    public void mongoTemplate() throws Exception {

        Mockito.when(environment.getProperty(RepositoryConfig.PROP_SRV_HOST)).thenReturn("192.168.1.127");
        Mockito.when(environment.getProperty(RepositoryConfig.PROP_DB_USER)).thenReturn("user");
        Mockito.when(environment.getProperty(RepositoryConfig.PROP_DB_PASSWORD)).thenReturn("pass");
        Mockito.when(environment.getProperty(RepositoryConfig.PROP_DB_NAME)).thenReturn("mydb");
        MongoDbFactory mongoDbFactory = Mockito.mock(MongoDbFactory.class);
        Mockito.when(mongoTools.mongoDbFactory("user", "pass", "mydb", null)).thenReturn(mongoDbFactory);

        MongoTemplate mongoTemplate = repositoryConfig.mongoTemplate();

        Assertions.assertThat(mongoTemplate).isNotNull();
        Assertions.assertThat(TestTools.getField(mongoTemplate, "mongoDbFactory", MongoDbFactory.class)).isEqualTo(mongoDbFactory);
        Assertions.assertThat(TestTools.getField(mongoTemplate, "writeConcernResolver", WriteConcernResolver.class)).isEqualTo(writeConcernResolver);
    }

    @Test
    public void mongoFactoryBean() throws Exception {

        Mockito.when(environment.getProperty(RepositoryConfig.PROP_SRV_HOST)).thenReturn("192.168.1.127");
        MongoFactoryBean mongoFactoryBean = Mockito.mock(MongoFactoryBean.class);
        Mockito.when(mongoTools.mongoFactoryBean("192.168.1.127", null)).thenReturn(mongoFactoryBean);

        MongoFactoryBean factory = repositoryConfig.mongoFactoryBean();

        Assertions.assertThat(factory).isNotNull();
        Assertions.assertThat(factory).isEqualTo(mongoFactoryBean);

        Mockito.verify(mongoTools).mongoFactoryBean("192.168.1.127", null);
        Mockito.verify(environment, Mockito.times(2)).getProperty(Matchers.anyString());
        Mockito.verifyNoMoreInteractions(environment, mongoTools);
    }

    @Test
    public void mongoFactoryBeanWithPort() throws Exception {

        Mockito.when(environment.getProperty(RepositoryConfig.PROP_SRV_HOST)).thenReturn("192.168.1.127");
        Mockito.when(environment.getProperty(RepositoryConfig.PROP_SRV_PORT)).thenReturn("59878");
        MongoFactoryBean mongoFactoryBean = Mockito.mock(MongoFactoryBean.class);
        Mockito.when(mongoTools.mongoFactoryBean("192.168.1.127", 59878)).thenReturn(mongoFactoryBean);

        MongoFactoryBean factory = repositoryConfig.mongoFactoryBean();

        Assertions.assertThat(factory).isNotNull();
        Assertions.assertThat(factory).isEqualTo(mongoFactoryBean);

        Mockito.verify(mongoTools).mongoFactoryBean("192.168.1.127", 59878);
        Mockito.verify(environment, Mockito.times(2)).getProperty(Matchers.anyString());
        Mockito.verifyNoMoreInteractions(environment, mongoTools);
    }

    @Test
    public void mongoDbFactory() throws Exception {

        Mockito.when(environment.getProperty(RepositoryConfig.PROP_SRV_HOST)).thenReturn("192.168.1.127");
        Mockito.when(environment.getProperty(RepositoryConfig.PROP_DB_USER)).thenReturn("user");
        Mockito.when(environment.getProperty(RepositoryConfig.PROP_DB_PASSWORD)).thenReturn("pass");
        Mockito.when(environment.getProperty(RepositoryConfig.PROP_DB_NAME)).thenReturn("mydb");
        MongoDbFactory mongoDbFactory = Mockito.mock(MongoDbFactory.class);
        Mockito.when(mongoTools.mongoDbFactory("user", "pass", "mydb", null)).thenReturn(mongoDbFactory);

        MongoDbFactory factory = repositoryConfig.mongoDbFactory();

        Assertions.assertThat(factory).isNotNull();
        Assertions.assertThat(factory).isEqualTo(mongoDbFactory);

        Mockito.verify(environment, Mockito.times(5)).getProperty(Matchers.anyString());
        Mockito.verifyNoMoreInteractions(environment);
    }

}
