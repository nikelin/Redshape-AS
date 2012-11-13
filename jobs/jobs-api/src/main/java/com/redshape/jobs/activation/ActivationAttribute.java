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

package com.redshape.jobs.activation;

import com.redshape.utils.IEnum;

import java.util.Collection;
import java.util.HashSet;

/**
 * @author nikelin
 * @date 22/04/11
 * @package com.redshape.jobs.activation
 */
public class ActivationAttribute implements IEnum {
	private String name;

	protected ActivationAttribute( String name ) {
		this.name = name;
	}

	public static class Timer extends ActivationAttribute {
		protected Timer( String code ) {
			super(code);
		}

		public static final Timer Delay = new Timer("ActivationAttribute.Timer.Delay");
		public static final Timer Ticks = new Timer("ActivationAttribute.Timer.Ticks");
		public static final Timer Unlimited = new Timer("ActivationAttribute.Timer.Unlimited");
		public static final Timer Interval = new Timer("ActivationAttribute.Timer.Interval");
	}

	public static class Date extends ActivationAttribute {

		protected Date( String code ) {
			super(code);
		}

		public static final Timer Point = new Timer("ActivationAttribute.Timer.Point");

	}

	public static class Single extends ActivationAttribute {

		protected Single( String code ) {
			super(code);
		}

		public static final Single Delay = new Single("ActivationAttribute.Timer.Delay");

	}

	public static Collection<ActivationAttribute> VALUES = new HashSet<ActivationAttribute>();
	{
		VALUES.add( Timer.Delay );
		VALUES.add( Timer.Ticks );
		VALUES.add( Timer.Unlimited );
		VALUES.add( Timer.Interval );
		VALUES.add( Date.Point );
		VALUES.add( Single.Delay );
	}

	@Override
	public String name() {
		return this.name;
	}

	@Override
	public String toString() {
		return this.name;
	}

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

    @Override
    public boolean equals( Object value ) {
        if ( value != null && value instanceof ActivationAttribute ) {
            return this.name().equals( ( (ActivationAttribute ) value).name() );
        }

        return false;
    }

}