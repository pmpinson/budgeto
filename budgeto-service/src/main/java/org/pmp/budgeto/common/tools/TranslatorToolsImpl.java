package org.pmp.budgeto.common.tools;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * implementations of the translator common
 * need MessageSource dependence from Qualifier i18n
 */
// TODO manage multiple language from user preferences
@Component
public class TranslatorToolsImpl implements TranslatorTools {

    private final MessageSource messageSource;

    @Autowired
    public TranslatorToolsImpl(@Qualifier("i18n") MessageSource messageSource) {
        this.messageSource = Validate.notNull(messageSource);
    }

    @Override
    public Locale getLocale() {
        return Locale.FRANCE;
    }

    @Override
    public String get(String id) {
        return messageSource.getMessage(id, new Object[]{}, getLocale());
    }

    @Override
    public String get(String id, Object... params) {
        return messageSource.getMessage(id, params, getLocale());
    }

}
