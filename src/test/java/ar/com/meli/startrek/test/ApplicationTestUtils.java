package ar.com.meli.startrek.test;

import java.lang.reflect.Method;

public class ApplicationTestUtils {

    public static Method getPrivateMethod(Class<?> clazz, String method, Class<?>... parameters) throws NoSuchMethodException, SecurityException {
        Method _method = clazz.getDeclaredMethod(method, parameters);
        _method.setAccessible(true);
        return _method;
    }
}
