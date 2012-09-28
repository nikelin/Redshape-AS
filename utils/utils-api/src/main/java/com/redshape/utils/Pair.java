package com.redshape.utils;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.utils
 * @date 1/31/12 {6:26 PM}
 */
public class Pair<K, V> {
    
    private K key;
    private V value;
    
    public Pair( K key, V value ) {
        this.key = key;
        this.value = value;
    }
    
    public K getKey() {
        return this.key;
    }
    
    public V getValue() {
        return this.value;
    }
    
}
