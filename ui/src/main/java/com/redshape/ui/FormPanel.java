package com.redshape.ui;

import com.redshape.ui.events.UIEvents;
import com.redshape.ui.validators.IValidator;

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
	
	private Map<String, FormField<?>> fields = new HashMap<String, FormField<?>>();
    protected JComponent centerPane;
    protected JComponent buttonsPane;

    public FormPanel() {
        super();

        this.buildUI();
        this.configUI();
    }
    
    protected void configUI() {
    	this.setLayout( new GridLayout( 0, 1 ) );
    }

    protected void buildUI() {
    	this.centerPane = new JLayeredPane();
        this.centerPane.setLayout( new GridLayout(0, 2) );
        this.add( this.centerPane );

        this.buttonsPane = new JLayeredPane();
        this.buttonsPane.setLayout( new FlowLayout( FlowLayout.RIGHT ) );
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
    	
    	return result;
    }
    
    @SuppressWarnings("unchecked")
	public <T> FormField<T> getField( String id ) {
        return (FormField<T>) this.fields.get(id);
    }

    protected <T> FormField<T> createField( String label, JComponent field ) {
        return new FormField<T>(label, field);
    }

    public void addButton( JButton button ) {
        this.buttonsPane.add(button);
    }

    public <T> void addField( String id, String label, JComponent component ) {
        FormField<T> field = this.<T>createField(label, component );
        this.fields.put( id, field );
        this.centerPane.add( field.getLabel() );
        this.centerPane.add( field.getComponent() );
    }

    @Override
    public void doLayout() {
        super.doLayout();

        Dispatcher.get().forwardEvent(UIEvents.Core.Repaint);
    }

    public class FormField<T> extends JComponent {
		private static final long serialVersionUID = -6212118336463716252L;
		
		private JLabel labelComponent;
        private JComponent field;
        private IValidator<T> validator;

        public FormField( String label, JComponent field ) {
            this.labelComponent = new JLabel( label );
            this.field = field;
            
            this.init();
        }
        
        protected void init() {
        	this.field.addKeyListener( new KeyAdapter() {
				@Override
				public void keyTyped(KeyEvent e) {
					FormField.this.isDataValid();
				}
			});
        }
        
        public void setValidator( IValidator<T> validator ) {
        	this.validator = validator;
        }
        
        public boolean isDataValid() {
        	if ( this.validator == null || this.validator.isValid( this.getValue() ) ) {
        		this.markValid();
        		return true;
        	}
        	
        	this.markInvalid();
        	
        	return false;
        }
        
        public void setValue( Object value ) {
        	if ( this.getComponent() instanceof JTextComponent ) {
        		( (JTextComponent) this.getComponent() ).setText( String.valueOf( value ) );
        		this.getComponent().revalidate();
        	} else if ( this.getComponent() instanceof ItemSelectable ) {
        		
        	}
        }
        
        @SuppressWarnings("unchecked")
		public T getValue() {
            if ( this.getComponent() instanceof JTextComponent ) {
                return (T) ( (JTextComponent) this.getComponent() ).getText();
            } else if ( this.getComponent() instanceof  ItemSelectable ) {
                return (T) ( (ItemSelectable) this.getComponent() ).getSelectedObjects();
            }

            return null;
        }
        
        public void markInvalid() {
        	this.getComponent().setBackground( new Color( 255, 0, 0 ) );
        }
        
        public void markValid() {
        	this.getComponent().setBackground( new Color( 255, 255, 255 ) );
        }

        public JLabel getLabel() {
            return this.labelComponent;
        }

        public JComponent getComponent() {
            return this.field;
        }
    }
}
