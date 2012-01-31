package com.redshape.utils.streams;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.utils.streams
 * @date 1/31/12 {5:18 PM}
 */
public class Priority implements Comparable<Priority> {
    private int value;
    
    protected Priority( int value ) {
        this.value = value;
    }
    
    public static final Priority RT = new Priority(4);
    public static final Priority HIGH = new Priority(3);
    public static final Priority NORMAL = new Priority(2);
    public static final Priority LOW = new Priority(1);

    @Override
    public int compareTo(Priority o) {
        return o.value > this.value ? -1 : ( o.value == this.value ? 0 : 1 );
    }

    @Override
    public int hashCode() {
        return this.value;
    }

    @Override
    public String toString() {
        return String.valueOf( this.value );
    }
}
