/*
 * Copyright 2012 Cyril A. Karpenko
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.redshape.ui.panels;

import com.redshape.ui.data.IModelData;
import com.redshape.ui.data.IModelField;
import com.redshape.ui.data.IModelType;
import com.redshape.utils.validators.IValidator;

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
    private int lastY;
    
    public FormPanel() {
    	this(null, false);
    }

    public FormPanel( IModelType type, boolean writable ) {
        this( null, type, writable );
    }

    public FormPanel( IModelData data, IModelType type, boolean writable ) {
        super();

        this.type = type;
        this.typeWritable = writable;
        
        this.buildUI();
        this.configUI();
        this.init();

        if ( data != null ) {
            this.setModel(data);
        }
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
    			formField.setValue( String.valueOf( data.<Object>get( field.getName() ) ) );
    		}
    	}
    }
    
    protected void configUI() {
    	this.setLayout( new BoxLayout( this, BoxLayout.Y_AXIS ) );
    }

    protected void buildUI() {
    	this.centerPane = new JPanel();
        this.centerPane.setLayout( new GridBagLayout() );
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
    
    public Map<String, Object> getValues() {
        Map<String, Object> result = new HashMap<String, Object>();
        for ( FormField<?> field : this.getFields() ) {
            result.put( String.valueOf( field.getId() ), field.getValue() );
        }
        
        return result;
    }

	public void removeFields() {
        FormField<?>[] fields = this.fields.values().toArray( new FormField<?>[ this.fields.size() ]);
        for ( int i = 0; i < fields.length; i++ ) {
            this.removeField( fields[i] );
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
        int row = this.lastY++;
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridwidth = 1;
        constraints.weightx = 1;
        constraints.gridx = 0;
        constraints.gridy = row;
        constraints.fill = GridBagConstraints.HORIZONTAL;

    	this.centerPane.add( component, constraints );
    }
    
    public <T> FormField<T> addField( Object id, String label, JComponent component ) {
        int row = this.lastY++;
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

        if ( this.isComplex( component ) ) {
            componentConstraints.gridy = this.lastY++;
            componentConstraints.gridx = 0;
            componentConstraints.gridwidth = 2;
            labelConstraints.gridwidth = 2;
            labelConstraints.weightx = 1;
            labelConstraints.gridx = 0;
            labelConstraints.gridwidth = 2;
            componentConstraints.weightx = 1;
        }

        FormField<T> field = this.<T>createField( id, label, component );
        this.fields.put( id, field );
        this.centerPane.add( field.getLabel(), labelConstraints );
        this.centerPane.add( field.getComponent(), componentConstraints );
        
        return field;
    }

    protected boolean isComplex( JComponent component ) {
        return component instanceof JComboBox
                || component instanceof JList
                || component instanceof JTextArea
                || component instanceof JTable
                || component instanceof JScrollPane
                || component instanceof JPanel;
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
        		( (JComboBox) this.getComponent() ).setSelectedItem( value );
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
