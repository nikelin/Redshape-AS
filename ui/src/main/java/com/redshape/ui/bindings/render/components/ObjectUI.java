package com.redshape.ui.bindings.render.components;

import java.awt.Component;
import java.awt.Dimension;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import org.apache.log4j.Logger;

import com.redshape.bindings.BeanConstructor;
import com.redshape.bindings.BeanInfo;
import com.redshape.bindings.IBeanInfo;
import com.redshape.bindings.types.IBindable;
import com.redshape.ui.UnhandledUIException;
import com.redshape.ui.bindings.views.IComposedModel;
import com.redshape.ui.bindings.views.IPropertyModel;
import com.redshape.ui.utils.UIRegistry;
import com.redshape.utils.StringUtils;

public class ObjectUI extends JPanel implements Cloneable {
	private static final Logger log = Logger.getLogger( ObjectUI.class );
	private static final long serialVersionUID = 216700183533225126L;
	
	private IComposedModel model;
	private Collection<ObjectUI> childs = new HashSet<ObjectUI>();
	private Map<IPropertyModel, JComponent> fields = new HashMap<IPropertyModel, JComponent>();
	
	private JComponent contentPane;
	private JComponent buttonsPane;
	
	public ObjectUI() {
		this(null);
	}
	
	public ObjectUI( IComposedModel model ) {
		super();
		this.model = model;
		
		this.configUI();
		this.buildUI();
	}
	
	protected void buildUI() {
		JPanel panel = new JPanel();
		panel.setLayout( new BoxLayout( panel, BoxLayout.Y_AXIS ) );
		panel.add( this.contentPane = this.createContentPane() );
		panel.add( this.buttonsPane = this.createButtonsPane() );
		this.add( panel );
	}
	
	protected JComponent createButtonsPane() {
		return new JPanel();
	}
	
	protected JComponent createContentPane() {
		JComponent result = new JPanel();
		result.setLayout( new BoxLayout( result, BoxLayout.Y_AXIS ) );
		return result;
	}
	
	protected IComposedModel getModel() {
		return this.model;
	}
	
	public void addButton( JButton button ) {
		this.buttonsPane.add( button );
	}
	
	public JComponent getField( String id ) {
		for ( IPropertyModel model : this.fields.keySet() ) {
			if ( model.getId().equals(id) ) {
				return this.fields.get(model);
			}
		}
		
		return null;
	}
	
	public ObjectUI getNested( String id ) {
		for ( ObjectUI nestedUI : this.childs ) {
			if ( id.equals( nestedUI.getModel().getId() ) ) {
				return nestedUI;
			}
		}
		
		return null;
	}
	
	protected JLabel createFieldLabel( IPropertyModel model ) {
		JLabel fieldLabel = new JLabel( StringUtils.ucfirst( model.getTitle() ) );
		fieldLabel.setAlignmentX( LEFT_ALIGNMENT );
		return fieldLabel;
	}
	
	public void addField( IPropertyModel model, JComponent component ) {
		JPanel box = new JPanel();
		box.setMinimumSize( new Dimension(500, 50 ) );
		box.add( this.createFieldLabel( model ) );
		box.add( component );
		
		this.fields.put( model, component );
		
		this.contentPane.add( box );
	}
	
	public void addObject( ObjectUI ui ) {
		this.childs.add(ui);
		this.contentPane.add(ui);
	}
	
	protected Border createTitle( String title ) {
		return BorderFactory.createTitledBorder( title != null ? StringUtils.ucfirst( title ) : "" );
	}
	
	protected void configUI() {
		this.setLayout( new BoxLayout( this, BoxLayout.Y_AXIS ) );
		this.setMinimumSize( new Dimension(500, 50 ) );
		
		if ( null != this.model ) {
			this.setBorder( this.createTitle( this.model.getTitle() ) );
		}
	}
	
	protected Object getValue( IPropertyModel model ) {
		JComponent component = this.fields.get(model);
		if ( component instanceof JTextField ) {
			return this.getValue( model, (JTextField) component );
		} else if ( component instanceof JCheckBox ){
			return this.getValue( model, (JCheckBox) component );
		} else {
			throw new IllegalArgumentException();
		}
	}
	
	protected Object getValue( IPropertyModel model, JTextField field ) {
		switch ( model.getDescriptor().getMetaType() ) {
		case BOOLEAN:
			return Boolean.valueOf( field.getText() );
		case STRING:
			return String.valueOf( field.getText() );
		case NUMERIC:
			Class<?> type = model.getDescriptor().getType();
			String value = field.getText();
			if ( Float.class.equals(type) ) {
				return Float.valueOf( value );
			} else if ( Double.class.equals( type ) ) {
				return Double.valueOf( value );
			} else if ( Long.class.equals( type ) ) {
				return Long.valueOf( value );
			} else if ( Number.class.isAssignableFrom( type ) ) {
				return Integer.valueOf( value );
			}
		break;
		}
		
		throw new IllegalArgumentException("Unconvertible type");
	}
	
	protected Boolean getValue( IPropertyModel model, JCheckBox component ) {
		return component.isSelected();
	}
	
	@SuppressWarnings("unchecked")
	public <T> T createInstance() throws InstantiationException, NoSuchMethodException {
		try {
			IBeanInfo beanInfo = this.model.getDescriptor();
			BeanConstructor<T> constructor = beanInfo.getConstructor(new Class[] {});
			
			T object = constructor.newInstance(new Object[] {});
			
			for ( IPropertyModel model : this.fields.keySet() ) {
				model.getDescriptor().write(
					object,
					this.getValue( model )
				);
			}
			
			for ( ObjectUI nestedUI : this.childs ) {
				IComposedModel nestedModel = nestedUI.getModel();
				
				for ( IBindable bindable : beanInfo.getBindables() ) {
					if ( bindable.getId().equals( nestedModel.getId() ) ) {
						bindable.write( object, nestedUI.createInstance() );
					}
				}
			}
			
			return object;
		} catch ( Throwable e ) {
			log.error( e.getMessage(), e );
			throw new InstantiationException( e.getMessage() );
		}
	}
	
	public void updateInstance( Object object ) {
		
	}
	
	protected IBeanInfo createBeanInfo( Class<?> type ) {
		return new BeanInfo(type);
	}
	
	@Override
	public ObjectUI clone() {
		try {
			ObjectUI ui = new ObjectUI( this.model );
			ui.setLayout( new BoxLayout( ui, BoxLayout.Y_AXIS ) );
			
			for ( ObjectUI nested : this.childs ) {
				ObjectUI nestedObject = nested.clone();
				ui.addObject( nestedObject );
			}
			
			for ( IPropertyModel property : this.fields.keySet() ) {
				ui.addField( property, UIRegistry.getObjectsClonner().clone( this.fields.get(property) ) );
			}
			
			for ( Component button : this.buttonsPane.getComponents() ) {
				ui.addButton( (JButton) UIRegistry.getObjectsClonner().clone( button ) );
			}

			ui.revalidate();
			
			return ui;
		} catch ( Throwable e ) {
			throw new UnhandledUIException( e.getMessage(), e );
		}
	}
	
}
