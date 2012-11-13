/*
 * Copyright 2012 Cyril A. Karpenko
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

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
