package com.xwc.esbatis.assistant;


import com.xwc.esbatis.interfaces.BaseMapper;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 创建人：徐卫超
 * 创建时间：2019/4/2  11:26
 * 业务：
 * 功能：
 */
@SuppressWarnings("unused")
public class Reflection {


    private static final String SETTER = "set";
    private static final String GETTER = "get";

    public static <T> Method getter(Field field, Class<T> clazz) throws NoSuchMethodException {
        String fieldName = field.getName();
        String mthodeName = GETTER + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        return clazz.getMethod(mthodeName);
    }

    public static List<Field> getField(Class<?> typeClass) {
        Class<?> superclass = typeClass.getSuperclass();
        ArrayList<Field> list = new ArrayList<>();
        Field[] fields = typeClass.getDeclaredFields();
        for (Field field : fields) {
            list.add(field);
        }
        if (superclass != null) {
            list.addAll(getField(superclass));
        }
        return list;
    }

    public static <T> Method setter(Field field, Class<T> clazz) throws NoSuchMethodException {
        String fieldName = field.getName();
        String mthodeName = SETTER + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        return clazz.getMethod(mthodeName, field.getType());
    }


    public static Class<?> getEntityClass(Class<?> mapperClass) {
        Type[] types = mapperClass.getGenericInterfaces();
        for (Type type : types) {
            if (type instanceof ParameterizedType) {
                ParameterizedType t = (ParameterizedType) type;
                if (BaseMapper.class.isAssignableFrom((Class<?>) t.getRawType())) {
                    return (Class<?>) t.getActualTypeArguments()[0];
                }
            }
        }
        for (Type type : types) {
            if (type instanceof Class) {
                Class<?> clazz = getEntityClass((Class) type);
                if (clazz != null) {
                    return clazz;
                }
            }
        }
        return null;
    }

    public static boolean isPrimitive(Object obj) {
        try {
            return ((Class<?>) obj.getClass().getField("TYPE").get(null)).isPrimitive();
        } catch (Exception e) {
            return false;
        }
    }
}
