package com.redshape.ui.bindings.render.properties;

import javax.swing.*;

import com.redshape.bindings.annotations.BindableAttributes;
import com.redshape.bindings.types.IBindable;
import com.redshape.ui.UIException;
import com.redshape.ui.bindings.render.IViewRenderer;
import com.redshape.ui.data.IModelData;
import com.redshape.ui.data.adapters.swing.ComboBoxAdapter;
import com.redshape.ui.data.adapters.swing.ListAdapter;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Collection;

public class ListUI extends AbstractListUI<Object> {
	private static final long serialVersionUID = -461201665686274716L;

    private JComponent component;

	public ListUI( IViewRenderer<?> renderer, IBindable descriptor) throws UIException {
		super(renderer, descriptor);

        this.init();
	}

    @Override
    public JComponent asComponent() {
        return this;
    }

    protected void init() throws UIException {
        this.setLayout( new BoxLayout( this, BoxLayout.LINE_AXIS ) );

        this.add( this.component = this.createListElement() );
    }
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected JComponent createListElement() throws UIException {
        if ( !this.getDescriptor().hasAttribute( BindableAttributes.MULTICHOICE ) ) {
            return new ComboBoxAdapter( this.getProvider() );
        }

        return this.createListAdapter();
	}

    protected JComponent createListAdapter() throws UIException {
        JList list = new ListAdapter( this.getProvider() );
        list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        list.setVisibleRowCount(-1);
        list.setLayoutOrientation( JList.VERTICAL );

        return list;
    }

    protected boolean isMultiChoice() {
        return this.getDescriptor().hasAttribute( BindableAttributes.MULTICHOICE );
    }

	@Override
	public Object getValue() {
        if ( !this.isMultiChoice() ) {
            Object[] results = ( (ItemSelectable) this.component ).getSelectedObjects();
            if ( results.length > 0 ) {
                if ( results[0] instanceof IModelData ) {
                    return ( (IModelData) results[0] ).getRelatedObject();
                }
            } else {
                return null;
            }
        }

        Object[] result = ( (JList) this.component ).getSelectedValues();
        if ( result.length == 0 ) {
            return result;
        }

        if ( !( result[0] instanceof IModelData ) ) {
            return result;
        }

        List<Object> records = new ArrayList<Object>();
        for ( Object object : result ) {
            records.add( ( (IModelData) object ).getRelatedObject() );
        }

        return records;
	}

    @Override
    public void setValue( Object value ) {
        if ( !this.isMultiChoice() ) {
            ( (JComboBox) this.component ).setSelectedItem(value);
            return;
        }

        ( (JList) this.component ).setSelectedValue( value, true );
    }
}
