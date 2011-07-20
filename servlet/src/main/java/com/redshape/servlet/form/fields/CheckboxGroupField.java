package com.redshape.servlet.form.fields;

import com.redshape.servlet.form.render.impl.fields.CheckboxGroupFieldRenderer;

public class CheckboxGroupField<T> extends AbstractMultiSelectField<T> {
	private static final long serialVersionUID = 8858130225808419943L;

	public CheckboxGroupField() {
		this(null);
	}
	
	public CheckboxGroupField( String id ) {
		this(id, id);
	}
	
	public CheckboxGroupField( String id, String name ) {
		super(id, name);

        this.setRenderer( new CheckboxGroupFieldRenderer() );
	}

}
