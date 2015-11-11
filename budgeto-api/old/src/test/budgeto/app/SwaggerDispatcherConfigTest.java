package org.pmp.budgeto.app;

import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.models.dto.ApiInfo;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.pmp.budgeto.common.tools.TranslatorTools;
import org.pmp.budgeto.test.TestTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Locale;
import java.util.ResourceBundle;


@RunWith(MockitoJUnitRunner.class)
public class SwaggerDispatcherConfigTest {

    @Mock
    private TranslatorTools translatorTools;

    @Mock
    private SpringSwaggerConfig springSwaggerConfig;

    @InjectMocks
    private SwaggerDispatcherConfig swaggerDispatcherConfig;

    @Test
    public void structure() throws Exception {

        Assertions.assertThat(swaggerDispatcherConfig.getClass().getSuperclass()).isEqualTo(WebMvcConfigurerAdapter.class);
    }

    @Test
    public void springConf() throws Exception {

        Class<?> clazz = swaggerDispatcherConfig.getClass();
        Assertions.assertThat(clazz.getAnnotations()).hasSize(4);
        Assertions.assertThat(clazz.isAnnotationPresent(Configuration.class)).isTrue();
        Assertions.assertThat(clazz.isAnnotationPresent(EnableWebMvc.class)).isTrue();
        Assertions.assertThat(clazz.isAnnotationPresent(EnableSwagger.class)).isTrue();
        Assertions.assertThat(clazz.isAnnotationPresent(ComponentScan.class)).isTrue();
        Assertions.assertThat(clazz.getAnnotation(ComponentScan.class).basePackages()).containsExactly("com.ak.swaggerspringmvc.shared.app", "com.ak.spring3.music");

        Field fSpringSwaggerConfig = clazz.getDeclaredField("springSwaggerConfig");
        Assertions.assertThat(fSpringSwaggerConfig.getAnnotations()).hasSize(1);
        Assertions.assertThat(fSpringSwaggerConfig.isAnnotationPresent(Autowired.class)).isTrue();

        Method mCustomImplementation = clazz.getDeclaredMethod("customImplementation", new Class[]{});
        Assertions.assertThat(mCustomImplementation.getAnnotations()).hasSize(1);
        Assertions.assertThat(mCustomImplementation.getAnnotation(Bean.class)).isNotNull();
    }

    @Test
    public void customImplementation() throws Exception {
        ResourceBundle bundle = ResourceBundle.getBundle("i18n/common", Locale.ENGLISH);
        Mockito.when(translatorTools.get("app.title")).thenReturn(bundle.getString("app.title"));
        Mockito.when(translatorTools.get("app.description")).thenReturn(bundle.getString("app.description"));
        Mockito.when(translatorTools.get("app.terms")).thenReturn(bundle.getString("app.terms"));
        Mockito.when(translatorTools.get("app.contact")).thenReturn(bundle.getString("app.contact"));
        Mockito.when(translatorTools.get("app.licence")).thenReturn(bundle.getString("app.licence"));
        Mockito.when(translatorTools.get("app.licence.url")).thenReturn(bundle.getString("app.licence.url"));

        SwaggerSpringMvcPlugin swaggerSpringMvcPlugin = swaggerDispatcherConfig.customImplementation();

        Assertions.assertThat(swaggerSpringMvcPlugin).isNotNull();
        Assertions.assertThat(TestTools.getField(swaggerSpringMvcPlugin, "springSwaggerConfig", SpringSwaggerConfig.class)).isEqualTo(springSwaggerConfig);
        Assertions.assertThat(TestTools.getField(swaggerSpringMvcPlugin, "apiInfo", ApiInfo.class).getTitle()).isEqualTo("Budgeto api");
        Assertions.assertThat(TestTools.getField(swaggerSpringMvcPlugin, "apiInfo", ApiInfo.class).getDescription()).isEqualTo("Rest services for budgeto");
        Assertions.assertThat(TestTools.getField(swaggerSpringMvcPlugin, "apiInfo", ApiInfo.class).getTermsOfServiceUrl()).isEqualTo("");
        Assertions.assertThat(TestTools.getField(swaggerSpringMvcPlugin, "apiInfo", ApiInfo.class).getContact()).isEqualTo("pmpinson@gmail.com");
        Assertions.assertThat(TestTools.getField(swaggerSpringMvcPlugin, "apiInfo", ApiInfo.class).getLicense()).isEqualTo("no");
        Assertions.assertThat(TestTools.getField(swaggerSpringMvcPlugin, "apiInfo", ApiInfo.class).getLicenseUrl()).isEqualTo("");
    }

    @Test
    public void addResourceHandlers() throws Exception {

        ResourceHandlerRegistry resourceHandlerRegistry = Mockito.mock(ResourceHandlerRegistry.class);
        ResourceHandlerRegistration swaggerResourceHandlerRegistration = Mockito.mock(ResourceHandlerRegistration.class);
        Mockito.when(resourceHandlerRegistry.addResourceHandler(new String[]{"css/", "images/", "lib/", "swagger-ui.js"})).thenReturn(swaggerResourceHandlerRegistration);
        Mockito.when(swaggerResourceHandlerRegistration.addResourceLocations("classpath:META-INF/resources/")).thenReturn(swaggerResourceHandlerRegistration);
        Mockito.when(swaggerResourceHandlerRegistration.setCachePeriod(0)).thenReturn(swaggerResourceHandlerRegistration);
        ResourceHandlerRegistration indexResourceHandlerRegistration = Mockito.mock(ResourceHandlerRegistration.class);
        Mockito.when(resourceHandlerRegistry.addResourceHandler("index.html")).thenReturn(indexResourceHandlerRegistration);
        Mockito.when(indexResourceHandlerRegistration.addResourceLocations("classpath:swagger/")).thenReturn(indexResourceHandlerRegistration);
        Mockito.when(indexResourceHandlerRegistration.setCachePeriod(0)).thenReturn(indexResourceHandlerRegistration);

        swaggerDispatcherConfig.addResourceHandlers(resourceHandlerRegistry);

        Mockito.verify(resourceHandlerRegistry).addResourceHandler(new String[]{"css/", "images/", "lib/", "swagger-ui.js"});
        Mockito.verify(swaggerResourceHandlerRegistration).addResourceLocations("classpath:META-INF/resources/");
        Mockito.verify(swaggerResourceHandlerRegistration).setCachePeriod(0);
        Mockito.verify(resourceHandlerRegistry).addResourceHandler("index.html");
        Mockito.verify(indexResourceHandlerRegistration).addResourceLocations("classpath:swagger/");
        Mockito.verify(indexResourceHandlerRegistration).setCachePeriod(0);
        Mockito.verifyNoMoreInteractions(resourceHandlerRegistry, swaggerResourceHandlerRegistration, indexResourceHandlerRegistration);
    }

    @Test
    public void configureDefaultServletHandling() throws Exception {

        DefaultServletHandlerConfigurer defaultServletHandlerConfigurer = Mockito.mock(DefaultServletHandlerConfigurer.class);
        Mockito.doNothing().when(defaultServletHandlerConfigurer).enable();

        swaggerDispatcherConfig.configureDefaultServletHandling(defaultServletHandlerConfigurer);

        Mockito.verify(defaultServletHandlerConfigurer).enable();
        Mockito.verifyNoMoreInteractions(defaultServletHandlerConfigurer);
    }
}
