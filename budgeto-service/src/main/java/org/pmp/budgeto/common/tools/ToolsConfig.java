package org.pmp.budgeto.common.tools;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * config the common
 * load bundle : ToolsConfig.BUNDLE_NAMES from pass to use in translator common by MessageSource and Qualifier i18n
 * scan on org.pmp.budgeto.common.domain package
 */
@Configuration
@ComponentScan(basePackages = "org.pmp.budgeto.common.tools")
public class ToolsConfig {

    private static final String MESSAGE_ENCODING = "UTF-8";
    private static final String[] BUNDLE_NAMES = {"classpath:i18n/common", "classpath:i18n/budget", "classpath:i18n/account"};

    /**
     * load message bundles into spring messageSource.
     *
     * @return messageSource
     */
    @Bean
    @Qualifier("i18n")
    public MessageSource messageSource() {
        final ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames(BUNDLE_NAMES);
        messageSource.setDefaultEncoding(MESSAGE_ENCODING);
        return messageSource;
    }

}
