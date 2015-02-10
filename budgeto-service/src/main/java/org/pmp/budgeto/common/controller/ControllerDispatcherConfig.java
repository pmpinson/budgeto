package org.pmp.budgeto.common.controller;

import org.pmp.budgeto.common.tools.DateTools;
import java.util.List;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperFactoryBean;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * to configure the rest app servlet dispatcher
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"org.pmp.budgeto.common.controller"})
public class ControllerDispatcherConfig extends WebMvcConfigurerAdapter {

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        Jackson2ObjectMapperFactoryBean factory = new Jackson2ObjectMapperFactoryBean();
        factory.setIndentOutput(true);
        factory.setSimpleDateFormat(DateTools.PATTERN_DATETIMEMS_WITHZONE);
        factory.afterPropertiesSet();
        
        ObjectMapper objectMapper = factory.getObject();
        objectMapper.registerModule(new JodaModule());
        
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(objectMapper);
        
        converters.add(converter);
    }

}
