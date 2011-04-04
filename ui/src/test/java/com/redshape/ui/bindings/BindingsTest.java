package com.redshape.ui.bindings;

import java.util.Collection;

import javax.swing.JPanel;
import javax.swing.JTextField;

import org.junit.Test;
import static org.junit.Assert.*;

import com.redshape.bindings.annotations.Bindable;
import com.redshape.bindings.annotations.ElementType;
import com.redshape.bindings.types.BindableType;
import com.redshape.bindings.types.CollectionType;

import com.redshape.ui.UIException;
import com.redshape.ui.bindings.render.ISwingRenderer;
import com.redshape.ui.bindings.render.components.ObjectUI;
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
		
		@Bindable( name = "Other mocks", type = BindableType.COMPOSITE )
		@ElementType( value = TestMock2.class, type = CollectionType.LIST )
		public TestMock2[] mocks;
		
		@Bindable( name = "Target mocks collection")
		@ElementType( value = TestMock2.class, type = CollectionType.CHOICE )
		private Collection<TestMock2> targetMocks;
		
		public Collection<TestMock2> getTargetMocks() {
			return this.targetMocks;
		}
		
		public void setTargetMocks( Collection<TestMock2> mocks ) {
			this.targetMocks = mocks;
		}
		
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
	public void testMain() throws UIException, NoSuchMethodException, InstantiationException {
		ISwingRenderer renderer = UIRegistry.<ISwingRenderer>getViewRendererFacade()
											.createRenderer( TestMock.class );
		ObjectUI ui = renderer.render( new JPanel(), TestMock.class );
		assertNotNull( ui.getField("x") );
		assertEquals( JTextField.class, ui.getField("x").getClass() );
		String xValue = "xValue";
		( (JTextField) ui.getField("x") ).setText( xValue ); 
		assertNotNull( ui.getNested("y") );
		assertEquals( ObjectUI.class, ui.getNested("y").getClass() );
		assertNotNull( ui.getNested("y").getField("name") );
		assertEquals( JTextField.class, ui.getNested("y").getField("name").getClass() );
		String yNameValue = "yNameValue";
		( (JTextField) ui.getNested("y").getField("name") ).setText( yNameValue );
		
		TestMock mock = ui.createInstance();
		assertEquals( xValue, mock.x );
		assertEquals( mock.getY().name, mock.getY().name );
	}
	
}
