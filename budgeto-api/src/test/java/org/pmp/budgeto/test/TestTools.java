package org.pmp.budgeto.test;

import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Constructor;

/**
 * helper for test
 */
public class TestTools {

    private TestTools() {
        // nothing
    }

    /**
     * get content of a field by reflection
     *
     * @param target the target object
     * @param name   the name of filed to retrieve value
     * @param clazz  the class of value expected
     * @param <T>    the class of value
     * @return the value of field
     */
    public static <T> T getField(Object target, String name, Class<T> clazz) {
        return clazz.cast(ReflectionTestUtils.getField(target, name));
    }

    /**
     * set the content of a field by reflection
     *
     * @param target the target object
     * @param name   the name of filed to set value
     * @param value  the new value
     */
    public static void setField(Object target, String name, Object value) {
        ReflectionTestUtils.setField(target, name, value);
    }

    /**
     * test if consructor is unique and private
     *
     * @param clazz the class of value expected
     * @throws java.lang.Exception if error during reflexion on clazz
     */
    public static void callPrivateConstructor(Class<?> clazz) throws Exception {
        Constructor<?> constructor = clazz.getDeclaredConstructor();

        // create a new instance for test coverage
        constructor.setAccessible(true);
        constructor.newInstance();
        constructor.setAccessible(false);
    }

    public static ServletRequestAttributes mockServletRequestAttributes() {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        Mockito.when(request.getServerPort()).thenReturn(80);
        Mockito.when(request.getScheme()).thenReturn("http");
        Mockito.when(request.getServerName()).thenReturn("local");
        Mockito.when(request.getContextPath()).thenReturn("myapp");
        Mockito.when(request.getServletPath()).thenReturn("serv1");
        Mockito.when(request.getRequestURI()).thenReturn("http://local:80/myapp/serv1");

        return new ServletRequestAttributes(request);
    }

}
