package com.redshape.utils;

public class EnumUtils {
	
	@SuppressWarnings("unchecked")
	public static <T extends IEnum<T>> T[] allOf( Class<T> subject ) {
		try {
			return (T[]) subject.getMethod("values").invoke( subject , new Object[] {} );
		} catch ( Throwable e ) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends IEnum<T>> T valueOf( Class<T> subject, String name ) {
		try {
			return (T) subject.getMethod("valueOf", String.class).invoke( subject, name );
		} catch ( Throwable e ) {
			return null;
		}
	}
	
}
