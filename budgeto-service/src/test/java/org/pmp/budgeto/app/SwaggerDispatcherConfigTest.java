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
import org.pmp.budgeto.test.TestTools;
import org.pmp.budgeto.common.tools.TranslatorTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

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

        Assertions.assertThat(SwaggerDispatcherConfig.class.getSuperclass()).isEqualTo(WebMvcConfigurerAdapter.class);
    }

    @Test
    public void springConf() throws Exception {

        Assertions.assertThat(SwaggerDispatcherConfig.class.getAnnotations()).hasSize(4);
        Assertions.assertThat(SwaggerDispatcherConfig.class.isAnnotationPresent(Configuration.class)).isTrue();
        Assertions.assertThat(SwaggerDispatcherConfig.class.isAnnotationPresent(EnableWebMvc.class)).isTrue();
        Assertions.assertThat(SwaggerDispatcherConfig.class.isAnnotationPresent(EnableSwagger.class)).isTrue();
        Assertions.assertThat(SwaggerDispatcherConfig.class.isAnnotationPresent(ComponentScan.class)).isTrue();
        Assertions.assertThat(SwaggerDispatcherConfig.class.getAnnotation(ComponentScan.class).basePackages()).containsExactly("com.ak.swaggerspringmvc.shared.app", "com.ak.spring3.music");

        Assertions.assertThat(SwaggerDispatcherConfig.class.getDeclaredField("springSwaggerConfig").getAnnotations()).hasSize(1);
        Assertions.assertThat(SwaggerDispatcherConfig.class.getDeclaredField("springSwaggerConfig").isAnnotationPresent(Autowired.class)).isTrue();

        Assertions.assertThat(SwaggerDispatcherConfig.class.getDeclaredMethod("customImplementation", new Class[]{}).getAnnotations()).hasSize(1);
        Assertions.assertThat(SwaggerDispatcherConfig.class.getDeclaredMethod("customImplementation", new Class[]{}).getAnnotation(Bean.class)).isNotNull();
        Assertions.assertThat(SwaggerDispatcherConfig.class.getDeclaredMethod("getInternalResourceViewResolver", new Class[]{}).getAnnotations()).hasSize(1);
        Assertions.assertThat(SwaggerDispatcherConfig.class.getDeclaredMethod("getInternalResourceViewResolver", new Class[]{}).getAnnotation(Bean.class)).isNotNull();
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
        Assertions.assertThat(TestTools.getField(swaggerSpringMvcPlugin, "apiInfo", ApiInfo.class).getTitle()).isEqualTo("Budgeto service");
        Assertions.assertThat(TestTools.getField(swaggerSpringMvcPlugin, "apiInfo", ApiInfo.class).getDescription()).isEqualTo("Rest service for budgeto");
        Assertions.assertThat(TestTools.getField(swaggerSpringMvcPlugin, "apiInfo", ApiInfo.class).getTermsOfServiceUrl()).isEqualTo("");
        Assertions.assertThat(TestTools.getField(swaggerSpringMvcPlugin, "apiInfo", ApiInfo.class).getContact()).isEqualTo("pmpinson@gmail.com");
        Assertions.assertThat(TestTools.getField(swaggerSpringMvcPlugin, "apiInfo", ApiInfo.class).getLicense()).isEqualTo("no");
        Assertions.assertThat(TestTools.getField(swaggerSpringMvcPlugin, "apiInfo", ApiInfo.class).getLicenseUrl()).isEqualTo("");
    }

    @Test
    public void addResourceHandlers() throws Exception {

        ResourceHandlerRegistry resourceHandlerRegistry = Mockito.mock(ResourceHandlerRegistry.class);
        ResourceHandlerRegistration resourceHandlerRegistration = Mockito.mock(ResourceHandlerRegistration.class);
        Mockito.when(resourceHandlerRegistry.addResourceHandler(new String[]{"css/", "images/", "lib/", "swagger-ui.js"})).thenReturn(resourceHandlerRegistration);
        Mockito.when(resourceHandlerRegistration.addResourceLocations("classpath:META-INF/resources/")).thenReturn(resourceHandlerRegistration);
        Mockito.when(resourceHandlerRegistration.setCachePeriod(0)).thenReturn(resourceHandlerRegistration);

        swaggerDispatcherConfig.addResourceHandlers(resourceHandlerRegistry);

        Mockito.verify(resourceHandlerRegistry).addResourceHandler(new String[]{"css/", "images/", "lib/", "swagger-ui.js"});
        Mockito.verify(resourceHandlerRegistration).addResourceLocations("classpath:META-INF/resources/");
        Mockito.verify(resourceHandlerRegistration).setCachePeriod(0);
        Mockito.verifyNoMoreInteractions(resourceHandlerRegistry, resourceHandlerRegistration);
    }

    @Test
    public void getInternalResourceViewResolver() throws Exception {

        InternalResourceViewResolver resolver = swaggerDispatcherConfig.getInternalResourceViewResolver();

        Assertions.assertThat(resolver).isNotNull();
        Assertions.assertThat(TestTools.getField(resolver, "prefix", String.class)).isEqualTo("classpath:/resources/");
        Assertions.assertThat(TestTools.getField(resolver, "suffix", String.class)).isEqualTo(".jsp");
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
