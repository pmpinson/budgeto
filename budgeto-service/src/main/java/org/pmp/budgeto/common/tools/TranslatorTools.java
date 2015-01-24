package org.pmp.budgeto.common.tools;

import java.util.Locale;

/**
 * get the message from i18n bundle
 */
public interface TranslatorTools {

    /**
     * get message from current locale
     * @param id the id of message
     * @return the message
     */
    String get(String id);

    /**
     *
     * get message from current locale
     * @param id the id of message
     * @param params some object to parameteriz the message
     * @return the message
     */
    String get(String id, Object... params);

}
