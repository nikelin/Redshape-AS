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

package com.redshape.utils.events;

import org.apache.log4j.Logger;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * @author nikelin
 * @date 21/04/11
 * @package com.api.commons.events
 */
public class DelegateEventListener implements IEventListener<IEvent> {
	private static final Logger log = Logger.getLogger(DelegateEventListener.class);

	private String className;
	private String methodName;

	public DelegateEventListener( String className, String methodName ) {
		this.className = className;
		this.methodName = methodName;
	}

	@Override
	public void handleEvent(IEvent event) {
		try {
			Class<?> clazz = Class.forName( this.className );
			for ( Method m : clazz.getDeclaredMethods() ) {
				if ( m.getName().equals( this.methodName )
                        && m.getParameterTypes().length > 0
						    && IEvent.class.isAssignableFrom( m.getParameterTypes()[0] )
							    && Modifier.isStatic( m.getModifiers() ) ) {
					m.invoke( clazz, event );
					return;
				}
			}

			log.error("Delegate method not found...");
		} catch ( Throwable e ) {
			log.error("Handling exception!", e );
		}
	}

}
