package com.redshape.ui.bindings;

import org.junit.Test;

import com.redshape.bindings.annotations.Bindable;
import com.redshape.ui.bindings.test.AbstractContextAwareTest;
import com.redshape.ui.utils.UIConstants;
import com.redshape.ui.utils.UIRegistry;

public class BindingsTest extends AbstractContextAwareTest<Object> {
	private static final String CONTEXT_PATH = "src/test/resources/context.xml";
	
	public static class TestMock2 {
		@Bindable( name = "Jelly" )
		public String name;
		
	}
	
	public static class TestMock {
		
		@Bindable( name = "Field x name" )
		public String x;
		
		@Bindable( name = "Afla!")
		private TestMock2 y;
		
		public void setY( TestMock2 y ) {
			this.y = y;
		}
		
		public TestMock2 getY() {
			return this.y;
		}
	}
	
	public BindingsTest() {
		super( CONTEXT_PATH );
		
		UIRegistry.set( UIConstants.System.SPRING_CONTEXT, this.getContext() );
	}
	
	@Test
	public void testMain() {
		ObjectUI<TestMock> ui = new ObjectUI<TestMock>( TestMock.class );
		
	}
	
}
