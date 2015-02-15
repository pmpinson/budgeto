package org.pmp.budgeto.common.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import org.pmp.budgeto.common.tools.DateTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperFactoryBean;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * to configure the rest app servlet dispatcher
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"org.pmp.budgeto.common.controller"})
public class ControllerDispatcherConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private DateTools dateTools;


    public MappingJackson2HttpMessageConverter jacksonConverter() {
        Jackson2ObjectMapperFactoryBean factory = new Jackson2ObjectMapperFactoryBean();
        factory.setIndentOutput(true);
        factory.setSimpleDateFormat(dateTools.getPatternDatetimeWithzone());
        factory.afterPropertiesSet();

        ObjectMapper objectMapper = factory.getObject();
        objectMapper.registerModule(new JodaModule());

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(objectMapper);
        return converter;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(jacksonConverter());
    }

}
