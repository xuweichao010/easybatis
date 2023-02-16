package cn.onetozero.easy.parse.utils;


import cn.onetozero.easy.parse.exceptions.EasyException;
import cn.onetozero.easy.parse.supports.EasyMapper;

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
    private static final String IS = "is";

    public static <T> Method getter(Field field, Class<T> clazz) throws NoSuchMethodException {
        if (boolean.class.isAssignableFrom(field.getType()) || Boolean.class.isAssignableFrom(field.getType())) {
            String fieldName = field.getName();
            String methodName = GETTER + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            try {
                return clazz.getMethod(methodName);
            } catch (NoSuchMethodException e) {
                methodName = IS + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                return clazz.getMethod(methodName);
            }
        } else {
            String fieldName = field.getName();
            String methodName = GETTER + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            return clazz.getMethod(methodName);
        }

    }

    public static List<Field> getField(Class<?> typeClass) {
        Class<?> superclass = typeClass.getSuperclass();
        ArrayList<Field> list = new ArrayList<>();
        Field[] fields = typeClass.getDeclaredFields();
        for (Field field : fields) {
            if (field.getType().getClassLoader() == null) {
                list.add(field);
            }
        }
        if (superclass.equals(Object.class)) {
            return list;
        }
        list.addAll(getField(superclass));
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
                if (EasyMapper.class.isAssignableFrom((Class<?>) t.getRawType())) {
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

    public static Class<?> geCollectionEntityClass(Class<?> mapperClass) {
        Type[] types = mapperClass.getGenericInterfaces();
        for (Type type : types) {
            if (type instanceof ParameterizedType) {
                ParameterizedType t = (ParameterizedType) type;
                if (EasyMapper.class.isAssignableFrom((Class<?>) t.getRawType())) {
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

    public static Method chooseMethod(Class<?> classType, String methodName) {
        Method[] declaredMethod = classType.getMethods();
        for (Method method : declaredMethod) {
            if (method.getName().equals(methodName)) {
                return method;
            }
        }
        throw new EasyException("找不到方法");
    }

    public static boolean isPrimitive(Object obj) {
        try {
            return ((Class<?>) obj.getClass().getField("TYPE").get(null)).isPrimitive();
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isCustomObject(Class<?> clazz) {
        return clazz.getClassLoader() != null;
    }
}
