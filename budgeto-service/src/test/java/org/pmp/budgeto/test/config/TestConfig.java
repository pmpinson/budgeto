package org.pmp.budgeto.test.config;

import org.pmp.budgeto.common.tools.LocaleTools;
import org.pmp.budgeto.common.tools.LocaleToolsImpl;
import org.pmp.budgeto.common.tools.ToolsConfig;
import org.pmp.budgeto.common.domain.DomainTools;
import org.pmp.budgeto.common.domain.DomainToolsImpl;
import org.pmp.budgeto.common.tools.TranslatorTools;
import org.pmp.budgeto.common.tools.TranslatorToolsImpl;
import org.pmp.budgeto.common.tools.ValidatorTools;
import org.pmp.budgeto.common.tools.ValidatorToolsImpl;

/**
 * Helper to configure test scope and inject dependencies
 */
public class TestConfig {

    public static final LocaleTools localeTools = new LocaleToolsImpl();

    /**
     * get a translator common
     * @return the common
     */
    public static TranslatorTools translatorTools() {
        final TranslatorTools translatorTools = new TranslatorToolsImpl(new ToolsConfig().messageSource(), localeTools);
        return translatorTools;
    }

    /**
     * get a org common
     * @return the common
     */
    public static ValidatorTools validatorTools() {
        ValidatorTools validatorTools = new ValidatorToolsImpl();
        return validatorTools;
    }

    /**
     * get a domain exception common
     * @return the common
     */
    public static DomainTools serviceExceptionTools() {
        DomainTools domainTools = new DomainToolsImpl(translatorTools(), validatorTools());
        return domainTools;
    }

}
