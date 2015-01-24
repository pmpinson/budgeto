package org.pmp.budgeto.test.config;

import org.pmp.budgeto.common.domain.DomainTools;
import org.pmp.budgeto.common.domain.DomainToolsImpl;
import org.pmp.budgeto.common.tools.*;

/**
 * Helper to configure test scope and inject dependencies
 */
public class TestConfig {

    public static final LocaleTools localeTools = new LocaleToolsImpl();

    public static final ValidatorTools validatorTools = new ValidatorToolsImpl();

    public static final TranslatorTools translatorTools = new TranslatorToolsImpl(new ToolsConfig().messageSource(), localeTools);

    public static final DomainTools domainTools = new DomainToolsImpl(translatorTools, validatorTools);

    public static void init() {
        ((LocaleToolsImpl) localeTools).init();
    }

}
