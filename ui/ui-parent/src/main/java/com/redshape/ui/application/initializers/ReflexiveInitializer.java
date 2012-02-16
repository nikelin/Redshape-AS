package com.redshape.ui.application.initializers;

import java.lang.reflect.Method;

import com.redshape.ui.application.IController;
import com.redshape.ui.application.IControllerInitializer;
import com.redshape.ui.application.annotations.Action;
import com.redshape.ui.application.events.AppEvent;
import com.redshape.ui.application.events.EventType;
import com.redshape.ui.application.events.IEventHandler;
import com.redshape.utils.Commons;

public class ReflexiveInitializer implements IControllerInitializer {

	public static class MethodHandler implements IEventHandler {
		private IController controller;
		private Method method;
		
		public MethodHandler( IController controller, Method method ) {
			Commons.checkNotNull(controller);
			Commons.checkNotNull(method);
			
			this.controller = controller;
			this.method = method;
		}

		@Override
		public void handle(AppEvent event) {
			try {
				int paramsLength = this.method.getParameterTypes().length;
				if ( paramsLength == 1 ) {
					this.method.invoke( this.controller, event);
				} else if ( paramsLength == 0 ) {
					this.method.invoke( this.controller );
				}
			} catch ( Throwable e ) {
				throw new IllegalStateException( e.getMessage(), e );
			}
		}
		
	}
	
	@Override
	public void init(IController controller) {
		for ( Method method : controller.getClass().getMethods() ) {
            Action annotation = method.getAnnotation( Action.class );
            if ( annotation == null ) {
                continue;
            }

            if ( annotation.eventType() == null  ) {
            	continue;
            }
            
            int parametersLength = method.getParameterTypes().length;
            if ( parametersLength != 0 && parametersLength != 1 ) {
            	throw new IllegalArgumentException("Not suitable method passed as actions handler: " +
            			"wrong income arguments count");
            }
            
            try {
                controller.registerHandler( 
            		EventType.valueOf( annotation.eventType() ), 
            		new MethodHandler(controller, method) 
        		);
        	} catch ( Throwable e ) {
                break;
            }
        }
	}

}
