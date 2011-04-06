package com.redshape.ui.bindings;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JComboBox;
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
import com.redshape.ui.data.ModelDataTest;
import com.redshape.ui.data.StoreTest;
import com.redshape.ui.data.loaders.AbstractDataLoader;

import com.redshape.ui.utils.UIConstants;
import com.redshape.ui.utils.UIRegistry;
import com.redshape.utils.IEnum;

public class BindingsTest extends AbstractContextAwareTest<Object> {
	private static final String CONTEXT_PATH = "src/test/resources/context.xml";
	
	public static class TestMock2 {
		@Bindable( name = "Jelly" )
		public String name;
		
	}
	
	public enum X {
		A,
		B,
		C
	}
	
	public static class Z {
		public String name;
		
		public Z( String name ) {
			this.name = name;
		}
	}
	
	public static class SuchLikeEnum implements IEnum<SuchLikeEnum> {
		private static final long serialVersionUID = -5965430052457355767L;

		private String code;
		
		public static final SuchLikeEnum E1 = new SuchLikeEnum("E1?");
		
		public static final SuchLikeEnum[] VALUES = new SuchLikeEnum[] { E1 };
		
		protected SuchLikeEnum( String code ) {
			this.code = code;
		}
		
		public static SuchLikeEnum[] values() {
			return VALUES;
		}
		
		@Override
		public String name() {
			return this.code;
		}
	}
	
	public static class Device {
		
		public String name;
		
		public Device( String name ) {
			this.name = name;
		}
		
		@Override
		public String toString() {
			return this.name;
		}
		
	}
	
	public static class TestMock {
		
		@Bindable( name = "Such like enum test ;-)")
		public SuchLikeEnum suchLikeEnumeration;
		
		@Bindable( name = "Enum test" )
		public X enumeration;
		
		@Bindable( name = "Select device", type = BindableType.LIST )
		@ElementType( value = Z.class, type = CollectionType.CHOICE )
		public Z device;
		
		@Bindable( name = "Field x name" )
		public String x;
		
		@Bindable( name = "Afla!" )
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
		
		UIRegistry.getProvidersFactory().registerProvider(TestMock2.class, StoreTest.class );
		UIRegistry.getProvidersFactory().registerProvider(Z.class, StoreTest.class );
		
		UIRegistry.getProvidersFactory().registerLoader( 
			renderer, StoreTest.class, new AbstractDataLoader<ModelDataTest>() {
			@Override
			protected List<ModelDataTest> doLoad() {
				List<ModelDataTest> result = new ArrayList<ModelDataTest>();
				result.add( this.createRecord("a") );
				result.add( this.createRecord("b") );
				
				return result;
			}
			
			private ModelDataTest createRecord( String name ) {
				ModelDataTest dataTest = new ModelDataTest(name);
				dataTest.setRelatedObject( new Z(name) );
				
				return dataTest;
			}
		});
		
		ObjectUI ui = renderer.render( new JPanel(), TestMock.class );
		assertNotNull( ui.getField("device") );
		assertTrue( JComboBox.class.isAssignableFrom( ui.getField("device").getClass() ) );
		( (JComboBox) ui.getField("enumeration") ).setSelectedIndex(1);
		assertNotNull( ui.getField("enumeration") );
		assertEquals( JComboBox.class, ui.getField("enumeration").getClass() );
		( (JComboBox) ui.getField("enumeration") ).setSelectedItem( X.A );
		assertNotNull( ui.getField("suchLikeEnumeration") );
		assertTrue( JComboBox.class.isAssignableFrom( ui.getField("suchLikeEnumeration").getClass() ) );
		( (JComboBox) ui.getField("suchLikeEnumeration") ).setSelectedItem( SuchLikeEnum.E1 );
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
		assertEquals( "a", mock.device.name );
		assertEquals( xValue, mock.x );
		assertEquals( SuchLikeEnum.E1, mock.suchLikeEnumeration );
		assertEquals( X.A, mock.enumeration );
		assertEquals( mock.getY().name, mock.getY().name );
	}
	
}
