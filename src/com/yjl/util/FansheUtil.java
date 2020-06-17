package com.yjl.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class FansheUtil {

    public static<T> T getInstance(Class<T> clazz, String[] names, Object[] values) {
        try {
            T t = clazz.newInstance();
            //遍历字段数组，通过调用对应的方法给对应的字段赋值
            for (int i=0; i<names.length; i++) {
                Method method = getMethod(names[i], clazz);
                method.invoke(t, values[i]);
            }
            return t;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Method getMethod(String fieldName, Class clazz)
            throws NoSuchFieldException, NoSuchMethodException {
        //给id属性赋值
        StringBuffer setMethodName = new StringBuffer();
        setMethodName.append("set");
        setMethodName.append(fieldName.substring(0,1).toUpperCase());//I
        setMethodName.append(fieldName.substring(1));//d

        Field field = clazz.getDeclaredField(fieldName);
        Class<?> type = field.getType();
        Method method = clazz.getMethod(setMethodName.toString(), new Class[]{type});
        return method;
    }
}
