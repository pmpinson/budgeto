package org.pmp.budgeto.common.tools;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

/**
 * implementations of the translator common
 * need MessageSource dependence from Qualifier i18n
 */
@Component
public class TranslatorToolsImpl implements TranslatorTools {

    private final MessageSource messageSource;

    private final LocaleTools localeTools;

    @Autowired
    public TranslatorToolsImpl(@Qualifier("i18n") MessageSource messageSource, LocaleTools localeTools) {
        this.messageSource = Validate.notNull(messageSource);
        this.localeTools = Validate.notNull(localeTools);
    }

    @Override
    public String get(String id) {
        return messageSource.getMessage(id, new Object[]{}, localeTools.getLocale());
    }

    @Override
    public String get(String id, Object... params) {
        return messageSource.getMessage(id, params, localeTools.getLocale());
    }

}
