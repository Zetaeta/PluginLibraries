package net.zetaeta.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ReflectionUtil {
    @SuppressWarnings("unchecked")
    public static <T> T getField(Object container, String fieldName) throws Exception {
        Class<?> containerClass = container.getClass();
        Field f = containerClass.getField(fieldName);
        T t = (T) f.get(container);
        return t;
    }
    
    public static Method getMethod(Object container, String methodName) {
        Class<?> containerClass = container.getClass();
        for (Method poss : containerClass.getDeclaredMethods()) {
            if (poss.getName().equals(methodName)) {
                return poss;
            }
        }
        return null;
    }
    
    public static Field[] getAllFields(Class<?> clazz) {
        List<Field> fields = new ArrayList<>();
        for (; clazz.getSuperclass() != Object.class; clazz = clazz.getSuperclass()) {
            Field[] classFields = clazz.getDeclaredFields();
            for (Field field : classFields) {
                fields.add(field);
            }
        }
        return fields.toArray(new Field[fields.size()]);
    }
}
