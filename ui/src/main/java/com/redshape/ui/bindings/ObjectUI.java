package com.redshape.ui.bindings;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.redshape.bindings.BeanInfo;
import com.redshape.bindings.BindingException;
import com.redshape.bindings.IBeanInfo;
import com.redshape.bindings.types.IBindable;
import com.redshape.ui.UnhandledUIException;
import com.redshape.ui.utils.UIRegistry;

public class ObjectUI<T> extends JComponent implements IObjectUI<T> {
	private static final long serialVersionUID = -7992916878461901388L;
	
	private Class<T> context;
	private Map<String, IPropertyUI<?>> propertyHolders 
			= new HashMap<String, IPropertyUI<?>>();
	
	public ObjectUI( Class<T> clazz ) {
		super();
		
		this.context = clazz;
		
		this.configUI();
	}
	
	protected Class<T> getContext() {
		return this.context;
	}
	
	protected IObjectUIBuilder getObjectsUIBuilder() {
		return UIRegistry.getContext().getBean( IObjectUIBuilder.class );
	}
	
	protected IPropertyUIBuilder getPropertyUIBuilder() {
		return UIRegistry.getContext().getBean( IPropertyUIBuilder.class );
	}
	
	@Override
	public void paint(Graphics g) {
		JPanel box = new JPanel();
		this.setLayout( new BoxLayout( this, BoxLayout.PAGE_AXIS ) );
		
		try {
			IBeanInfo beanInfo = this.createBeanInfo( this.context );
			for ( IBindable property : beanInfo.getBindables() ) {
				box.add( this.createPropertyUI(property) );
			}
		} catch ( UnhandledUIException e ) {
			throw e;
		} catch ( Throwable e ) {
			throw new UnhandledUIException( "Object UI creation exception", e );
		}
		
		this.add(box);
		
		super.paint(g);
	}
	
	@SuppressWarnings({ "rawtypes" })
	protected JComponent createPropertyUI( IBindable property ) {
		if ( property.isComposite() ) {
			return this.getObjectsUIBuilder().createUI( property.getType() ).asComponent();
		} 
		
		Box box = Box.createHorizontalBox();
		box.setPreferredSize( new Dimension(150, 25) );
		
		box.add( new JLabel( property.getName() ) );
		
		try {
			IPropertyUI renderer = this.getPropertyUIBuilder().createRenderer( property.getType() );
			this.propertyHolders.put( property.getId(), renderer );
			// FIXME
			box.add( renderer.renderEditor() );
		} catch ( InstantiationException e  ) {
			throw new UnhandledUIException( e.getMessage(), e );
		}
		
		return box;
	}
	
	private IBeanInfo createBeanInfo( Class<T> context ) {
		return new BeanInfo( context );
	}
	
	protected void configUI() {
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <V> V asComponent() {
		return (V) this;
	}

	@Override
	public T synthesize() throws BindingException {
		return null;
	}
	
}
