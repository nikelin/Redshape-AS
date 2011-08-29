package com.redshape.utils;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Most common static functions.
 * 
 * @author nikelin
 */
public final class Commons {

    /**
     * Conditional select operator which select B or C dependent on E equality to null
     *
     * @param <T>
     * @param condition
     * @param first
     * @param second
     *
     * @return
     */
    public static <T> T select( Object condition, T first, T second ) {
        return condition != null ? first : second;
    }

	/**
	 * Simple selector which is analogy for JS operator || in a case of binary value
	 * selection ( a || b ).
	 * 
	 * @param <T>
	 * @param first
	 * @param second
	 * @return
	 */
	public static <T> T select( T first, T second ) {
		return first == null ? second : first;
	}
	
	@SuppressWarnings("unchecked")
	public static <K, V> Map<K, V> map( List<K> keys, List<V> values ) {
		return Commons.map( (K[]) keys.toArray(), (V[]) values.toArray() );
	}
	
	/**
	 * Construct map from a given key and value collections.
	 * 
	 * @param <K>
	 * @param <V>
	 * @param keys
	 * @param values
	 * @return
	 */
	public static <K, V> Map<K, V> map( K[] keys, V[] values ) {
		Map<K, V> result = new HashMap<K, V>();
		for ( int i = 0; i < keys.length; i ++ ) {
			result.put( keys[i], values[i] );
		}
		return result;
	}


	public static Map<String, Object> map( Class<? extends Enum> enumClazz ) {
		try {
			Map<String, Object> result = new HashMap<String, Object>();
			Enum[] enumValues = (Enum[]) enumClazz.getMethod("values").invoke(null);
			for ( Enum enumValue : enumValues ) {
				result.put( enumValue.name(), enumValue.ordinal() );
			}

			return result;
		} catch ( Throwable e ) {
			return null;
		}
	}

	public static <T, V> T switchEnum( IEnum<V> value, Map<IEnum<V>, IFunction<?, T>> values ) {
		return switchEnum( value, values, null );
	}

	public static <T, V> T switchEnum( IEnum<V> value, Map<IEnum<V>, IFunction<?, T>> values,
															 IFunction<?, T> defaultCase ) {
		IEnum<V> resultCase = null;
		for ( IEnum<V> enumMember : values.keySet() ) {
			if ( enumMember.equals(value) ) {
				resultCase = enumMember;
				break;
			}
		}

		try {
			if ( resultCase == null ) {
				if ( defaultCase != null ) {
					defaultCase.invoke();
				}

				return null;
			}

			return values.get(resultCase).invoke();
		} catch ( InvocationTargetException e ) {
			throw new IllegalStateException("Case activation failed", e.getTargetException() );
		}
	}
	
}
