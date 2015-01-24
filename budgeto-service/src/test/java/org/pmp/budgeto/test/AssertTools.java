package org.pmp.budgeto.test;

import org.assertj.core.api.Assertions;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

/**
 * helper for test
 */
public class AssertTools {

    private AssertTools() {
        // nothing
    }

    /**
     * test if consructor is unique and private
     *
     * @param clazz the class of value expected
     * @throws java.lang.Exception if error during reflexion on clazz
     */
    public static void onePrivateConstructorForUtilityClass(Class<?> clazz) throws Exception {
        // test 1 constructor
        Assertions.assertThat(clazz.getConstructors()).hasSize(1);

        // controle if constructor private
        Constructor<?> constructor = clazz.getDeclaredConstructor();
        Assertions.assertThat(Modifier.isPrivate(constructor.getModifiers())).isTrue();

        // create a new instance for test coverage
        constructor.setAccessible(true);
        constructor.newInstance();
        constructor.setAccessible(false);
    }

}
