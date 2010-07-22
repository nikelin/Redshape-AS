/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vio.utils;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author nikelin
 */
public class BaseReflectionUtil implements ReflectionUtil {
    private static BaseReflectionUtil _instance = new BaseReflectionUtil();

    public static BaseReflectionUtil getInstance() {
        return _instance;
    }

    @Override
    public Class<?> findClass(String name, String clsPackage) throws ClassNotFoundException {
        String path = StringUtils.toCamelCase(name);
        if (clsPackage != null) {
            path = clsPackage + '.' + path;
        }

        return Class.forName(path);
    }

    public Object invokeMethod(String name, Object context) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return this.invokeMethod(name, context, new Object[] {} );
    }

    @Override
    public Object invokeMethod(String name, Object context, Object... args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> cls = context instanceof Class<?> ? (Class<?>) context : context.getClass();

        ArrayList<Class<?>> argsClasses = new ArrayList<Class<?>>();
        if (args != null) {
            for (Object arg : args) {
                argsClasses.add(arg.getClass());
            }
        }

        for (Method method : cls.getDeclaredMethods()) {
            if (method.getName().equals(name)
                    && (argsClasses.size() == 0 || checkMethodParameters(method, argsClasses))
                    && Modifier.isPublic(method.getModifiers())) {
                return method.invoke(context, args);
            }
        }

        throw new NoSuchMethodException(name);
    }

    /**
     * Check that class instantiates by singleton way, and if it does returns
     * instantiator method.
     *
     * @param Class<?> context
     * @return boolean
     */
    public Method getSingletonInstantiator(Class<?> context) {
        /**
         * Check that class have static field to store singleton instance
         */
        Field sField = null;
        for (Field f : context.getFields()) {
            if (!f.getType().getName().equals(context.getName())) {
                continue;
            }

            if (!Modifier.isStatic(f.getModifiers())) {
                continue;
            }

            sField = f;
        }

        if (null == sField) {
            return null;
        }

        /**
         * Check that all constructors is not public (main property)
         */
        for (Constructor<?> c : context.getConstructors()) {
            if (Modifier.isPublic(c.getModifiers())) {
                return null;
            }
        }

        Method sMethod = null;
        for (Method m : context.getMethods()) {
            if (!Modifier.isStatic(m.getModifiers())) {
                continue;
            }

            if (!Modifier.isPublic(m.getModifiers())) {
                continue;
            }

            if (!m.getReturnType().getName().equals(context.getName())) {
                continue;
            }

            if (m.getParameterTypes().length > 0) {
                continue;
            }

            sMethod = m;
        }

        return sMethod;
    }

    /**
     * First this method try to check is given class some part of singleton. If he does,
     * we try to find instantiator in class definition.
     * If class is not singleton we look for first accessible non-parametrized constructor
     * and call it.
     *
     * @param Class<?> context
     * @return Method
     * @throws InvocationTargetException
     * @throws IllegalArgumentException
     * @throws InstantiationException
     * @fixme This method works only with not generic classes.
     */
    public Object createInstance(Class<?> context) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
        Object result = null;

        Method instantiator = this.getSingletonInstantiator(context);
        if (null != instantiator) {
            result = instantiator.invoke(context);
        } else {
            for (Constructor<?> c : context.getConstructors()) {
                if (Modifier.isPublic(c.getModifiers()) && c.getParameterTypes().length == 0) {
                    result = c.newInstance();
                }
            }
        }

        return result;
    }

    @Override
    public String getActualParameterType(ParameterizedType type) {
        return type.getActualTypeArguments()[0].toString().replace("class ", "");
    }

    public boolean checkMethodParameters(Method method, List<Class<?>> classes) {
        boolean result = false;

        int equalsCount = 0;
        for (Class<?> parameterClass : method.getParameterTypes()) {
            for (Class<?> cls : classes) {
                if (isParent(parameterClass, cls) || cls.getName().equals(parameterClass.getName())) {
                    equalsCount++;
                    break;
                }
            }
        }

        if (equalsCount == method.getParameterTypes().length) {
            result = true;
        }

        return result;
    }

    public boolean isParent(Class<?> parent, Class<?> child) {
        Class<?> pParent = parent;
        Class<?> cParent = child;
        while (pParent != cParent && pParent != null && cParent != null) {
            // Чтобы если parent - конечный предок, метод возвращал правильный результат
            pParent = pParent.getSuperclass() != null ? pParent.getSuperclass() : pParent;
            cParent = cParent.getSuperclass();
        }

        return pParent == cParent;
    }

    @Override
    public void setFieldValue(String name, Object value, Object context) throws NoSuchFieldException, IllegalAccessException {
        try {
            // First look for user
            this.invokeMethod("set" + StringUtils.toCamelCase(name), context, value);
        } catch (Exception e) {
            Field field = context.getClass().getDeclaredField(name);
            if (Modifier.isPublic(field.getModifiers())) {
                field.set(context, value);
            }
        }
    }

    public Class<?> getClassForType(ParameterizedType type) {
        Type actualType = type.getActualTypeArguments()[0];
        // Huk to get actual name of generic type class
        String actualClassName = actualType.toString().replace("class ", "");

        Class<?> result = null;
        try {
            result = Class.forName(actualClassName);
        } catch (ClassNotFoundException e) {
            // not need in exception handling
        }

        return result;
    }

    @Override
    public Object initializeField(Field field, Object context) throws IllegalAccessException, InstantiationException, NoSuchFieldException {
        Object value = field.get(context);
        if (value == null) {
            try {
                this.invokeMethod("initialize" + StringUtils.toCamelCase( field.getName() ), context);
            } catch (Exception e) {
                value = field.getType().newInstance();
                field.set(context, value);
            }
        }

        return value;
    }

    @Override
    public Class<?> getClass(Type type) {
        if (type instanceof Class<?>) {
            return (Class<?>) type;
        } else if (type instanceof ParameterizedType) {
            return getClass(((ParameterizedType) type).getRawType());
        } else if (type instanceof GenericArrayType) {
            Type componentType = ((GenericArrayType) type).getGenericComponentType();
            Class<?> componentClass = getClass(componentType);
            if (componentClass != null) {
                return Array.newInstance(componentClass, 0).getClass();
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public <T> List<Class<?>> getTypeArguments(Class<T> baseClass, Class<? extends T> childClass) {
        Map<Type, Type> resolvedTypes = new HashMap<Type, Type>();
        Type type = childClass;
        // start walking up the inheritance hierarchy until we hit baseClass
        while (!getClass(type).equals(baseClass)) {
            if (type instanceof Class) {
                // there is no useful information for us in raw types, so just keep going.
                type = ((Class) type).getGenericSuperclass();
            } else {
                ParameterizedType parameterizedType = (ParameterizedType) type;
                Class<?> rawType = (Class) parameterizedType.getRawType();

                Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                TypeVariable<?>[] typeParameters = rawType.getTypeParameters();
                for (int i = 0; i < actualTypeArguments.length; i++) {
                    resolvedTypes.put(typeParameters[i], actualTypeArguments[i]);
                }

                if (!rawType.equals(baseClass)) {
                    type = rawType.getGenericSuperclass();
                }
            }
        }

        // finally, for each actual type argument provided to baseClass, determine (if possible)
        // the raw class for that type argument.
        Type[] actualTypeArguments;
        if (type instanceof Class) {
            actualTypeArguments = ((Class) type).getTypeParameters();
        } else {
            actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();
        }
        List<Class<?>> typeArgumentsAsClasses = new ArrayList<Class<?>>();
        // resolve types by chasing down type variables.
        for (Type baseType : actualTypeArguments) {
            while (resolvedTypes.containsKey(baseType)) {
                baseType = resolvedTypes.get(baseType);
            }
            typeArgumentsAsClasses.add(getClass(baseType));
        }
        return typeArgumentsAsClasses;
    }
}
