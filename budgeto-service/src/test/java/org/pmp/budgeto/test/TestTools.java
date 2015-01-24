package org.pmp.budgeto.test;

import org.springframework.test.util.ReflectionTestUtils;

/**
 * helper for test
 */
public class TestTools {

    private TestTools() {
        // nothing
    }

    /**
     * get content of a field by reflection
     * @param target the target object
     * @param name the name of filed to retrieve value
     * @param clazz the class of value expected
     * @param <T> the class of value
     * @return the value of field
     */
    public static <T> T getField(Object target, String name, Class<T> clazz) {
        return clazz.cast(ReflectionTestUtils.getField(target, name));
    }

    /**
     * set the content of a field by reflection
     * @param target the target object
     * @param name the name of filed to set value
     * @param value the new value
     */
    public static void setField(Object target, String name, Object value) {
        ReflectionTestUtils.setField(target, name, value);
    }

}
