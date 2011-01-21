package com.redshape.ui;

import com.redshape.ui.events.UIEvents;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 07.01.11
 * Time: 15:29
 * To change this template use File | Settings | File Templates.
 */
public class FormPanel extends JPanel {
    private Map<String, JComponent> fields = new HashMap<String, JComponent>();
    protected JComponent centerPane;
    protected JComponent buttonsPane;

    public FormPanel() {
        super();

        this.buildUI();
    }

    protected void buildUI() {
        this.setLayout( new GridLayout( 0, 1 ) );

        this.centerPane = new JLayeredPane();
        this.centerPane.setLayout( new GridLayout(0, 2) );
        this.add( this.centerPane );

        this.buttonsPane = new JLayeredPane();
        this.buttonsPane.setLayout( new FlowLayout( FlowLayout.RIGHT ) );
        this.add( this.buttonsPane );
    }

    public <V> V getValue( String id ) {
        JComponent component = this.fields.get(id);
        if ( component instanceof JTextComponent ) {
            return (V) ( (JTextComponent) component).getText();
        } else if ( component instanceof  ItemSelectable ) {
            return (V) ( (ItemSelectable) component ).getSelectedObjects();
        }

        return null;
    }

    public JComponent getField( String id ) {
        return this.fields.get(id);
    }

    protected FormField createField( String label, JComponent field ) {
        return new FormField(label, field);
    }

    public void addButton( JButton button ) {
        this.buttonsPane.add(button);
    }

    public void addField( String id, String label, JComponent component ) {
        FormField field = this.createField(label, component );
        this.fields.put( id, component );
        this.centerPane.add( field.getLabel() );
        this.centerPane.add( field.getComponent() );
    }

    @Override
    public void doLayout() {
        super.doLayout();

        Dispatcher.get().forwardEvent(UIEvents.Core.Repaint);
    }

    public class FormField extends JComponent {
        private JLabel labelComponent;
        private JComponent field;

        public FormField( String label, JComponent field ) {
            this.labelComponent = new JLabel( label );
            this.field = field;
        }

        public JLabel getLabel() {
            return this.labelComponent;
        }

        public JComponent getComponent() {
            return this.field;
        }
    }
}
