package org.pmp.budgeto.app;

import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.models.dto.ApiInfo;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;
import org.ajar.swaggermvcui.SwaggerSpringMvcUi;
import org.pmp.budgeto.common.tools.TranslatorTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * to configure the swagger doc servlet dispatcher
 */
@Configuration
@EnableWebMvc
@EnableSwagger
@ComponentScan(basePackages = {"com.ak.swaggerspringmvc.shared.app", "com.ak.spring3.music"})
public class SwaggerDispatcherConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private SpringSwaggerConfig springSwaggerConfig;

    @Autowired
    private TranslatorTools translatorTools;

    /**
     * configure specific api info, disabled default message
     *
     * @return the config of swagger plugin
     */
    @Bean
    public SwaggerSpringMvcPlugin customImplementation() {
        final SwaggerSpringMvcPlugin swaggerSpringMvcPlugin = new SwaggerSpringMvcPlugin(springSwaggerConfig);
        String title = translatorTools.get("app.title");
        String description = translatorTools.get("app.description");
        String terms = translatorTools.get("app.terms");
        String contact = translatorTools.get("app.contact");
        String licence = translatorTools.get("app.licence");
        String licenceUrl = translatorTools.get("app.licence.url");
        swaggerSpringMvcPlugin.apiInfo(new ApiInfo(title, description, terms, contact, licence, licenceUrl));

        swaggerSpringMvcPlugin.useDefaultResponseMessages(false);
        return swaggerSpringMvcPlugin;
    }

    /**
     * configure SwaggerSpringMvcUi.WEB_JAR_RESOURCE_PATTERNS to be in resource jar to be response by http
     *
     * @param registry to register information
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(SwaggerSpringMvcUi.WEB_JAR_RESOURCE_PATTERNS)
                .addResourceLocations(SwaggerSpringMvcUi.WEB_JAR_RESOURCE_LOCATION).setCachePeriod(0);

        registry.addResourceHandler("index.html")
                .addResourceLocations("classpath:swagger/").setCachePeriod(0);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("redirect:index.html");
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

}
