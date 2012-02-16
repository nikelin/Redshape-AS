package com.redshape.form.fields;

import com.redshape.renderer.forms.impl.fields.RadioGroupFieldRenderer;

public class RadioGroupField<T> extends AbstractSelectField<T> {
	private static final long serialVersionUID = 6859626264624176011L;

	public RadioGroupField() {
		this(null);
	}
	
	public RadioGroupField( String id ) {
		this(id, id);
	}
	
	public RadioGroupField( String id, String name ) {
		super(id, name );

        this.setRenderer( new RadioGroupFieldRenderer() );
	}
	
}
