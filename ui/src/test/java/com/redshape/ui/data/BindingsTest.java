package com.redshape.ui.data;

import com.redshape.bindings.BindingException;
import com.redshape.bindings.annotations.Bindable;
import com.redshape.bindings.annotations.ElementType;
import com.redshape.bindings.types.BindableType;
import com.redshape.bindings.types.CollectionType;
import com.redshape.ui.application.UIException;
import com.redshape.ui.data.bindings.render.ISwingRenderer;
import com.redshape.ui.data.bindings.render.components.ObjectUI;
import com.redshape.ui.data.bindings.render.properties.EnumUI;
import com.redshape.ui.data.bindings.render.properties.IEnumUI;
import com.redshape.ui.data.bindings.render.properties.ListUI;
import com.redshape.ui.data.bindings.render.properties.StringUI;
import com.redshape.ui.data.loaders.AbstractDataLoader;
import com.redshape.ui.utils.UIConstants;
import com.redshape.ui.utils.UIRegistry;
import com.redshape.utils.IEnum;
import com.redshape.utils.tests.AbstractContextAwareTest;
import org.junit.Test;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;

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
	public void testMain() throws UIException, NoSuchMethodException, BindingException, InstantiationException {
		ISwingRenderer renderer = UIRegistry.<ISwingRenderer>getViewRendererFacade()
											.createRenderer( TestMock.class );
		
		UIRegistry.getProvidersFactory().registerProvider(TestMock2.class, Store.class );
		UIRegistry.getProvidersFactory().registerProvider(Z.class, Store.class );
		
		UIRegistry.getProvidersFactory().registerLoader( 
			renderer, Store.class,
			new AbstractDataLoader<ModelData>() {
				private static final long serialVersionUID = 8104535800948094212L;
	
				@Override
				protected List<ModelData> doLoad() {
					List<ModelData> result = new ArrayList<ModelData>();
					result.add( this.createRecord("a") );
					result.add( this.createRecord("b") );
					
					return result;
				}
				
				private ModelData createRecord( String name ) {
					ModelData dataTest = new ModelData(name);
					dataTest.setRelatedObject( new Z(name) );
					
					return dataTest;
				}
		});
		
		ObjectUI ui = renderer.render( new JPanel(), TestMock.class );
		assertNotNull( ui.getField("device") );
		assertTrue( ListUI.class.isAssignableFrom( ui.getField("device").getClass() ) );
		( (JComboBox) ui.getField("enumeration") ).setSelectedIndex(1);
		assertNotNull( ui.getField("enumeration") );
		assertEquals( EnumUI.class, ui.getField("enumeration").getClass() );
		( (JComboBox) ui.getField("enumeration") ).setSelectedItem( X.A );
		assertNotNull( ui.getField("suchLikeEnumeration") );
		assertTrue( IEnumUI.class.isAssignableFrom( ui.getField("suchLikeEnumeration").getClass() ) );
		( (JComboBox) ui.getField("suchLikeEnumeration") ).setSelectedItem( SuchLikeEnum.E1 );
		assertNotNull( ui.getField("x") );
		assertEquals( StringUI.class, ui.getField("x").getClass() );
		String xValue = "xValue";
		( (JTextField) ui.getField("x") ).setText( xValue ); 
		assertNotNull( ui.getNested("y") );
		assertEquals( ObjectUI.class, ui.getNested("y").getClass() );
		assertNotNull( ui.getNested("y").getField("name") );
		assertEquals( StringUI.class, ui.getNested("y").getField("name").getClass() );
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
