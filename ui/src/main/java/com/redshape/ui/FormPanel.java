package com.redshape.ui;

import com.redshape.ui.data.IModelData;
import com.redshape.ui.data.IModelField;
import com.redshape.ui.data.IModelType;
import com.redshape.validators.IValidator;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 07.01.11
 * Time: 15:29
 * To change this template use File | Settings | File Templates.
 */
public class FormPanel extends JPanel {
	private static final long serialVersionUID = -4833457094699494483L;
	
	private Map<Object, FormField<?>> fields = new HashMap<Object, FormField<?>>();
	protected JComponent centerPane;
    protected JComponent buttonsPane;
    private IModelType type;
    private boolean typeWritable;
    @SuppressWarnings("unused")
	private IModelData model;
    
    public FormPanel() {
    	this(null, false);
    }
    
    public FormPanel( IModelType type, boolean writable ) {
        super();

        this.type = type;
        this.typeWritable = writable;
        
        this.buildUI();
        this.configUI();
        this.init();
    }
    
    protected void init() {
    	if ( this.type != null ) {
    		for ( IModelField field : this.type.getFields() ) {
    			this.addField( 
					field.getName(), 
					field.getTitle(), 
					this.typeWritable ? 
							this.createEditableComponent() 
							: this.createReadableComponent() 
					);
    		}
    	}
    }
    
    public void enableButtons( boolean value ) {
    	for ( Component component : this.buttonsPane.getComponents() ) {
    		if ( component instanceof JButton ) {
    			component.setEnabled(value);
    		}
    	}
    }
    
    protected JComponent createReadableComponent() {
    	return new JLabel();
    }
    
    protected JComponent createEditableComponent() {
    	return new JTextField();
    }
    
    public void setModel( IModelData data ) {
    	this.model = data;
    	
    	for ( IModelField field : this.type.getFields() ) {
    		FormField<?> formField = this.getField(field.getName());
    		if ( formField != null ) {
    			formField.setValue( data.<Object>get( field.getName() ) );
    		}
    	}
    }
    
    protected void configUI() {
    	this.setLayout( new BoxLayout( this, BoxLayout.Y_AXIS ) );
    }

    protected void buildUI() {
    	this.centerPane = new JPanel();
        this.centerPane.setLayout( new GridLayout(0, 2) );
        this.add( this.centerPane );

        this.buttonsPane = new JPanel();
        this.buttonsPane.setVisible(false);
        this.add( this.buttonsPane );
    }

    public void reset() {
    	for ( FormField<?> field : this.getFields() ) {
    		field.markValid();
    		field.setValue("");
    	}
    }
    
    public void markDataInvalid() {
    	for ( FormField<?> field : this.getFields() ) {
    		field.markInvalid();
    	}
    }

    public boolean isDataValid() {
    	boolean result = true;
    	for ( FormField<?> field : this.getFields() ) {
    		if ( !field.isDataValid() ) {
    			result = false;
    		}
    	}
    	
    	return result;
    }
    
    /** 
     * @TODO Refactoring needs
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
	public <T> Collection<FormField<T>> getFields() {
    	final Collection<FormField<T>> result = new HashSet<FormField<T>>();
    	
    	for ( FormField<?> field : this.fields.values() ) {
    		result.add( (FormField<T>) field );
    	}

		this.setLayout( new GridLayout( 0, 1 ) );

    	return result;
    }

	public void removeFields() {
		for ( int i = 0; i < this.fields.size(); i++ ) {
			this.removeField( this.fields.get(i) );
		}
	}

	public void removeField( String id ) {
		this.removeField( this.fields.get(id) );
	}

	public void removeField( FormField<?> field ) {
		this.centerPane.remove( field.getLabel() );
		this.centerPane.remove( field.getComponent() );
		this.fields.remove( field.getId() );
	}

    @SuppressWarnings("unchecked")
	public <T> FormField<T> getField( Object id ) {
        return (FormField<T>) this.fields.get(id);
    }

    protected <T> FormField<T> createField( Object id, String label, JComponent field ) {
        return new FormField<T>(id, label, field);
    }

    public void addButton( JButton button ) {
        this.buttonsPane.add(button);
        this.buttonsPane.setVisible(true);
    }
    
    public void addRaw( JComponent component ) {
    	this.centerPane.add( component );
    }
    
    public <T> FormField<T> addField( Object id, String label, JComponent component ) {
        FormField<T> field = this.<T>createField( id, label, component );
        this.fields.put( id, field );
        this.centerPane.add( field.getLabel() );
        this.centerPane.add( field.getComponent() );
        
        return field;
    }

    public class FormField<T> extends JComponent {
		private static final long serialVersionUID = -6212118336463716252L;
		
		private JLabel labelComponent;
        private JComponent field;
        private IValidator<T, ?> validator;
		private Object id;

        public FormField( Object id, String label, JComponent field ) {
			this.id = id;
            this.labelComponent = new JLabel( label );
            this.field = field;
            
            this.init();
        }

		public Object getId() {
			return this.id;
		}

        protected void init() {
        	this.field.addKeyListener( new KeyAdapter() {
				@Override
				public void keyTyped(KeyEvent e) {
					FormField.this.isDataValid();
				}
			});
        }
        
        public FormField<T> setValidator( IValidator<T, ?> validator ) {
        	this.validator = validator;
			return this;
        }
        
        public boolean isDataValid() {
        	if ( this.validator == null || this.validator.isValid( this.getValue() ) ) {
        		this.markValid();
        		return true;
        	}
        	
        	this.markInvalid();
        	
        	return false;
        }
        
        public FormField<T> setValue( Object value ) {
        	if ( this.getComponent() instanceof JTextComponent ) {
        		( (JTextComponent) this.getComponent() ).setText( String.valueOf( value ) );
        	} else if ( this.getComponent() instanceof JCheckBox ) {
        		( (JCheckBox) this.getComponent() ).setSelected( (Boolean) value );
        	} else if ( this.getComponent() instanceof JComboBox ) {
        		( (JComboBox) this.getComponent() ).setSelectedIndex( (Integer) value );
        	}
        	
        	this.getComponent().revalidate();

			return this;
        }
        
        @SuppressWarnings("unchecked")
		public T getValue() {
        	Object object = null;
            if ( this.getComponent() instanceof JTextComponent ) {
                object = ( (JTextComponent) this.getComponent() ).getText();
            } else if ( this.getComponent() instanceof JComboBox ) {
            	final JComboBox component = (JComboBox) this.getComponent();
            	object = component.getItemAt( component.getSelectedIndex() );
            } else if ( this.getComponent() instanceof JCheckBox ) {
            	object = ( (Boolean) ( (JCheckBox) this.getComponent() ).isSelected() );
            } else if ( this.getComponent() instanceof  ItemSelectable ) {
                object = ( (ItemSelectable) this.getComponent() ).getSelectedObjects();
            } 

            return (T) object;
        }
        
        public FormField<T> markInvalid() {
        	this.getComponent().setBackground( new Color( 255, 0, 0 ) );
			return this;
        }
        
        public FormField<T> markValid() {
        	this.getComponent().setBackground( new Color( 255, 255, 255 ) );
			return this;
        }

        public JLabel getLabel() {
            return this.labelComponent;
        }

        public JComponent getComponent() {
            return this.field;
        }
    }
}
