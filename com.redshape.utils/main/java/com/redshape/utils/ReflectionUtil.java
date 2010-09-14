/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.redshape.utils;

import java.lang.reflect.*;
import java.util.List;

/**
 * @author nikelin
 */
public interface ReflectionUtil {

    public Class<?> findClass(String name, String clsPackage) throws ClassNotFoundException;

    public Object invokeMethod(String name, Object context, Object... args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException;

    public String getActualParameterType(ParameterizedType type);

    public Object createInstance(Class<?> context) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException;

    public boolean checkMethodParameters(Method method, List<Class<?>> classes);

    public boolean isParent(Class<?> parent, Class<?> child);

    public void setFieldValue(String name, Object value, Object context) throws NoSuchFieldException, IllegalAccessException;

    public Class<?> getClass(Type t);

    public Object initializeField(Field field, Object context) throws IllegalAccessException, InstantiationException, NoSuchFieldException;

    public <T> List<Class<?>> getTypeArguments( Class<T> baseClass, Class<? extends T> childClass);
}
