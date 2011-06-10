package com.redshape.ui.data.bindings.render.components;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.border.Border;

import com.redshape.ui.application.UIException;
import com.redshape.ui.application.UnhandledUIException;
import com.redshape.ui.data.bindings.properties.IPropertyUI;
import com.redshape.utils.IFunction;
import org.apache.log4j.Logger;

import com.redshape.bindings.BeanConstructor;
import com.redshape.bindings.BindingException;
import com.redshape.bindings.IBeanInfo;
import com.redshape.bindings.types.IBindable;
import com.redshape.ui.data.bindings.views.IComposedModel;
import com.redshape.ui.data.bindings.views.IPropertyModel;
import com.redshape.ui.utils.UIRegistry;
import com.redshape.utils.StringUtils;

/**
 * Bean rendering result
 * 
 * @author nikelin
 */
public class ObjectUI extends JPanel implements Cloneable {
	private static final Logger log = Logger.getLogger( ObjectUI.class );
	private static final long serialVersionUID = 216700183533225126L;

	private IComposedModel model;
	private Collection<ObjectUI> childs = new HashSet<ObjectUI>();
	private Map<IPropertyModel, IPropertyUI<?>> fields = new HashMap<IPropertyModel, IPropertyUI<?>>();

    public enum HandlerType {
        PREINSTANTIATE,
        POSTINSTANTIATE
    }

	private Map<HandlerType, Collection<IFunction<?, ?>>> handlers
            = new HashMap<HandlerType, Collection<IFunction<?,?>>>();

    private int lastY;
    private List<JButton> buttons = new ArrayList<JButton>();
	
	public ObjectUI() {
		this(null);
	}
	
	public ObjectUI( IComposedModel model ) {
		super();
		this.model = model;

        this.handlers.put( HandlerType.POSTINSTANTIATE, new HashSet<IFunction<?,?>>() );
        this.handlers.put( HandlerType.PREINSTANTIATE, new HashSet<IFunction<?,?>>() );
		
		this.configUI();
		this.buildUI();
	}

    public void addPreInstantiateHandler( IFunction<?, ?> function ) {
        this.handlers.get( HandlerType.PREINSTANTIATE ).add( function );
    }

    public void addPostInstantiateHandler( IFunction<?, ?> function ) {
        this.handlers.get( HandlerType.POSTINSTANTIATE ).add( function );
    }

    protected Object processHandlers( IBeanInfo beanInfo, Object object ) throws InvocationTargetException {
        for ( IFunction<?, ?> function : this.handlers.get(HandlerType.POSTINSTANTIATE) ) {
            object = function.invoke( beanInfo, object );
        }

        return object;
    }

    protected Object processHandlers( IPropertyModel model, Object value ) throws InvocationTargetException {
        for ( IFunction<?,?> function : this.handlers.get( HandlerType.PREINSTANTIATE ) ) {
            value = function.invoke( model, value );
        }

        return value;
    }

	protected void buildUI() {
        this.setLayout(new GridBagLayout());
	}

	protected IComposedModel getModel() {
		return this.model;
	}

    public List<JButton> getButtons() {
        return this.buttons;
    }

    public void addButton( JButton button ) {
        GridBagConstraints c = new GridBagConstraints();
        c.gridy = 1 + ( this.lastY++ );
        this.addButton( button, c );
    }

	public void addButton( JButton button, Object constraints ) {
		this.add(button, constraints);
        this.buttons.add(button);
	}
	
	public IPropertyUI<?> getField( String id ) {
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
		JLabel fieldLabel = new JLabel( StringUtils.ucfirst( model.getTitle().isEmpty() ? model.getId() : model.getTitle() )  );
		fieldLabel.setAlignmentX( JLabel.LEFT );
		return fieldLabel;
	}

    protected boolean isComplex( JComponent component ) {
        return component instanceof JComboBox
                || component instanceof JList
                || component instanceof JTextArea
                    || component instanceof JTable
                        || component instanceof JScrollPane
                            || component instanceof JPanel;
    }

	public void addField( IPropertyModel model, IPropertyUI<?> component ) {
        int row = this.lastY++;

        JComponent cmp = component.asComponent();
        if ( cmp instanceof JList ) {
            cmp = new JScrollPane( cmp );
        }

        GridBagConstraints labelConstraints = new GridBagConstraints();
        labelConstraints.gridwidth = 1;
        labelConstraints.weightx = 0.3;
        labelConstraints.gridx = 0;
        labelConstraints.gridy = row;
        labelConstraints.fill = GridBagConstraints.HORIZONTAL;

        GridBagConstraints componentConstraints = new GridBagConstraints();
        componentConstraints.gridwidth = 1;
        componentConstraints.weightx = 0.7;
        componentConstraints.gridx = 1;
        componentConstraints.gridy = row;
        componentConstraints.fill = GridBagConstraints.HORIZONTAL;

        if ( this.isComplex( cmp ) ) {
            componentConstraints.gridy = this.lastY++;
            componentConstraints.gridx = 0;
            componentConstraints.gridwidth = 2;
            labelConstraints.gridwidth = 2;
            labelConstraints.weightx = 1;
            labelConstraints.gridx = 0;
            labelConstraints.gridwidth = 2;
            componentConstraints.weightx = 1;
        }

        this.add(this.createFieldLabel(model), labelConstraints);

        this.add(cmp, componentConstraints);
		
		this.fields.put( model, component );
	}
	
	public void addObject( ObjectUI ui ) {
		this.childs.add(ui);
		this.add(ui);
	}
	
	protected Border createTitle( String title ) {
		return BorderFactory.createTitledBorder( title != null ? StringUtils.ucfirst( title ) : "" );
	}
	
	protected void configUI() {
		this.setLayout( new BoxLayout( this, BoxLayout.Y_AXIS ) );
		
		if ( null != this.model ) {
			this.setBorder( this.createTitle( this.model.getTitle() ) );
		}
	}
	
	@SuppressWarnings("unchecked")
	public <T> T createInstance() throws BindingException, InstantiationException, NoSuchMethodException {
		try {
			IBeanInfo beanInfo = this.model.getDescriptor();
			BeanConstructor<T> constructor = beanInfo.getConstructor(new Class[] {});
			
			T object = constructor.newInstance(new Object[] {});
			
			for ( IPropertyModel model : this.fields.keySet() ) {
                Object value;
                try {
                    value = this.processHandlers( model, this.fields.get(model).getValue() );
                } catch ( InvocationTargetException e ) {
                    throw new BindingException("Pre-instantiate handlers exception!", e.getTargetException() );
                }

				model.getDescriptor().write(
					object,
					value
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

            try {
                object = (T) this.processHandlers( beanInfo, object );
            } catch ( InvocationTargetException e ) {
                throw new BindingException( "Post-instantiate handlers exception!", e.getTargetException() );
            }
			
			return object;
		} catch ( BindingException e ) {
            throw e;
        } catch ( Throwable e ) {
            log.error( e.getMessage(), e );
			throw new InstantiationException( e.getMessage() );
		}
	}
	
	@SuppressWarnings("unchecked")
	public <T> T updateInstance( T object ) throws BindingException {
		IBeanInfo beanInfo = this.model.getDescriptor();
		
		for ( IPropertyModel model : this.fields.keySet() ) {
            Object value;
            try {
                value = this.processHandlers( model, this.fields.get(model).getValue() );
            } catch ( InvocationTargetException e ) {
                throw new BindingException("Pre-instantiate handlers exception!", e.getTargetException() );
            } catch ( UIException e ) {
                throw new BindingException("Unable to retrieve field value");
            }

			model.getDescriptor().write(
				object,
				value
			);
		}
		
		for ( ObjectUI nestedUI : this.childs ) {
			IComposedModel nestedModel = nestedUI.getModel();
			
			for ( IBindable bindable : beanInfo.getBindables() ) {
				if ( bindable.getId().equals( nestedModel.getId() ) ) {
					bindable.write( object, nestedUI.updateInstance( bindable.read(object) ) );
				}
			}
		}

        try {
            object = (T) this.processHandlers( beanInfo, object );
        } catch ( InvocationTargetException e ) {
            throw new BindingException("Post-instantiate handlers exception!", e.getTargetException() );
        }
		
		return object;
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

            int i = 0;
            int row = 1 + ui.lastY++;
			for ( Component button : this.getButtons() ) {
                GridBagConstraints c = new GridBagConstraints();
                c.gridy = row;
                c.gridx = i++;
                c.weightx = 1 / this.getButtons().size();
				ui.addButton( (JButton) UIRegistry.getObjectsClonner().clone( button ), c );
			}

			ui.revalidate();
			
			return ui;
		} catch ( Throwable e ) {
			throw new UnhandledUIException( e.getMessage(), e );
		}
	}
	
}