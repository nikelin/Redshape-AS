package com.redshape.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

/**
 * Reflection utils
 *
 * @author Cyril A. Karpenko <self@nikelin>
 * @author Dmitry Novikoff <dnovikoff@gmail.com>
 */
public final class ReflectionUtils {

    public static Set<Class<?>> getAncestors( Class<?> source ) {
        Set<Class<?>> result = new HashSet<Class<?>>();
        result.addAll( getInterfaces(source, true) );
        result.addAll( getConcreteAncestors(source) );
        return result;
    }

    public static Set<Class<?>> getInterfaces( Class<?> source ) {
        return getInterfaces(source, false);
    }

    public static Set<Class<?>> getInterfaces( Class<?> source, boolean includeNested ) {
        Set<Class<?>> result = new HashSet<Class<?>>();

        for ( Class<?> sourceInterface : source.getInterfaces() ) {
            result.add( sourceInterface );
            if ( sourceInterface.getInterfaces().length != 0 ) {
                result.addAll( getInterfaces(sourceInterface, includeNested) );
            }
        }

        if ( includeNested &&
                source.getSuperclass() != null &&
                source.getSuperclass() != Object.class ) {
            result.addAll( getInterfaces(source.getSuperclass()) );
        }

        return result;
    }

    public static Set<Class<?>> getConcreteAncestors( Class<?> source ) {
        Set<Class<?>> result = new HashSet<Class<?>>();
        Class<?> superClass = source;
        do {
            superClass = superClass.getSuperclass();
             if ( superClass == Object.class ) {
                superClass = null;
            } else if ( superClass != null ) {
                result.add( superClass );
            }
        } while ( superClass != null );

        return  result;
    }

	public static boolean compareTypeLists( Class<?>[] first, Class<?>[] second ) {
		return compareTypeLists( first, second, false );
	}

	public static boolean compareTypeLists( Class<?>[] first, Class<?>[] second, boolean strict ) {
		for ( int i = 0; i < first.length; i++ ) {
			if ( strict ) {
				if ( second[i] == null || !first[i].equals(second[i]) ) {
					return false;
				}
			} else {
				if ( second[i] == null || !first[i].isAssignableFrom( second[i] ) ) {
					return false;
				}
			}
		}

		return true;
	}

    public static Class<?>[] getTypesList( Object... instances ) {
        Class<?>[] result = new Class[instances.length];
        for ( int i = 0; i < instances.length; i++ ) {
			if ( instances[i] == null ) {
				continue;
			}

            result[i] = instances[i].getClass();
        }

        return result;
    }

	public static String getSimpleClassName(Class<?> clazz) {
		if (!clazz.isMemberClass())
			return clazz.getSimpleName();

		String clazzName = null;
		Class currentClass = clazz;
		while (currentClass != null) {
			if (clazzName == null)
				clazzName = currentClass.getSimpleName();
			else
				clazzName = currentClass.getSimpleName() + "." + clazzName;
			currentClass = currentClass.getEnclosingClass();
		}

		return clazzName;
	}

	/**
	 * Для некоторого класса определяет каким классом был параметризован
     * один из его предков с generic-параметрами.
	 *
	 * @param actualClass   анализируемый класс
	 * @param genericClass  класс, для которого определяется значение параметра
	 * @param parameterIndex номер параметра
	 * @return класс, являющийся параметром с индексом parameterIndex в genericClass
	 */
	public static Class getGenericClassParameter(final Class actualClass, final Class genericClass, final int parameterIndex) {
		if (!genericClass.isAssignableFrom(actualClass.getSuperclass())) {
			throw new IllegalArgumentException("Class " + genericClass.getName() + " is not a superclass of " + actualClass.getName() + ".");
		}

		Stack<ParameterizedType> genericClasses = new Stack<ParameterizedType>();
		Class clazz = actualClass;

		while (true) {
			Type genericSuperclass = clazz.getGenericSuperclass();
			boolean isParameterizedType = genericSuperclass instanceof ParameterizedType;
			if (isParameterizedType) {
				genericClasses.push((ParameterizedType) genericSuperclass);
			} else {
				genericClasses.clear();
			}

			Type rawType = isParameterizedType ? ((ParameterizedType) genericSuperclass).getRawType() : genericSuperclass;

			if (rawType.equals(genericClass)) {
				break;
			}

			clazz = clazz.getSuperclass();
		}

		Type result = genericClasses.pop().getActualTypeArguments()[parameterIndex];

		while (result instanceof TypeVariable && !genericClasses.empty()) {
			int actualArgumentIndex = getParameterTypeDeclarationIndex((TypeVariable) result);
			ParameterizedType type = genericClasses.pop();
			result = type.getActualTypeArguments()[actualArgumentIndex];
		}

		if (result instanceof TypeVariable) {
			throw new IllegalStateException("Unable to resolve type variable " + result + "."
					+ " Try to replace instances of parametrized class with its non-parameterized subtype.");
		}

		if (result instanceof ParameterizedType) {
			result = ((ParameterizedType) result).getRawType();
		}

		if (result == null) {
			throw new IllegalStateException("Unable to determine actual parameter type for " + actualClass.getName() + ".");
		}

		if (!(result instanceof Class)) {
			throw new IllegalStateException("Actual parameter type for " + actualClass.getName() + " is not a Class.");
		}

		return (Class) result;
	}

	public static int getParameterTypeDeclarationIndex(final TypeVariable typeVariable) {
		GenericDeclaration genericDeclaration = typeVariable.getGenericDeclaration();

		TypeVariable[] typeVariables = genericDeclaration.getTypeParameters();
		Integer actualArgumentIndex = null;
		for (int i = 0; i < typeVariables.length; i++) {
			if (typeVariables[i].equals(typeVariable)) {
				actualArgumentIndex = i;
				break;
			}
		}

		if (actualArgumentIndex != null) {
			return actualArgumentIndex;
		}
		else {
			throw new IllegalStateException("Argument " + typeVariable.toString() + " is not found in " + genericDeclaration.toString() + ".");
		}
	}

	public static Class<?> getGenericPropertyClass(Class<?> clazz, PropertyDescriptor descriptor) {
		// Try to find getter
		Method readMethod = descriptor.getReadMethod();
		if (readMethod == null)
			return descriptor.getPropertyType();

		// Get generic return type
		String genericName = readMethod.getGenericReturnType().toString();

		// Get declaring class parameter index for our type
		Integer parameterIndex = null;
		Class declaringClass = readMethod.getDeclaringClass();
		TypeVariable[] typeParameters = declaringClass.getTypeParameters();
		for (int i = 0; i < typeParameters.length; i++) {
			if (!typeParameters[i].getName().equals(genericName))
				continue;

			parameterIndex = i;
			break;
		}

		// Throw an error if requested parameter not found
		if (parameterIndex == null || declaringClass.equals(clazz))
			return descriptor.getPropertyType();

		return getGenericClassParameter(clazz, declaringClass, parameterIndex);
	}

}
