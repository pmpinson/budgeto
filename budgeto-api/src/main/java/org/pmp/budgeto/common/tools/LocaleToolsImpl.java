package org.pmp.budgeto.common.tools;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Locale;

/**
 * implementations of the local tool
 */
@Component
public class LocaleToolsImpl implements LocaleTools {

    public static final Locale DEFAULT = Locale.ENGLISH;

    @PostConstruct
    public void init() {
        Locale.setDefault(DEFAULT);
    }

    @Override
    public Locale getLocale() {
        return DEFAULT;
    }

}
