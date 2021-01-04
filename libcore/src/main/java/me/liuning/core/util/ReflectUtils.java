package me.liuning.core.util;


import android.text.TextUtils;

import java.lang.reflect.Field;

public class ReflectUtils {

    private ReflectUtils() {

    }

    public static Object getFieldValue(Object instance, String fieldName) {

        if (instance == null || TextUtils.isEmpty(fieldName)) {
            return null;
        }

        Field field = getField(instance.getClass(), fieldName);
        if (field == null) {
            return null;
        }

        try {
            return field.get(instance);
        } catch (IllegalAccessException e) {
            //ignore
        }

        return null;

    }

    public static void setFieldValue(Object instance, String fieldName, Object value) {

        if (instance == null) {
            return;
        }
        Field field = getField(instance.getClass(), fieldName);
        if (field == null) {
            return;
        }

        try {
            field.set(instance, value);
        } catch (IllegalAccessException e) {
            //ignore
        }


    }


    public static Field getField(Class cls, String fieldName) {
        Field field;
        while (cls != null) {
            try {
                field = cls.getDeclaredField(fieldName);
                field.setAccessible(true);
                return field;
            } catch (NoSuchFieldException e) {
                //ignore
            }

            cls = cls.getSuperclass();

        }


        return null;

    }
}
