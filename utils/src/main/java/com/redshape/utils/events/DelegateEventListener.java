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
