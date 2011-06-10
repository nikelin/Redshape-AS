package com.redshape.servlet.form.fields;

import java.util.Collection;
import java.util.HashSet;

import com.redshape.servlet.form.AbstractFormItem;
import com.redshape.servlet.form.IFormField;
import com.redshape.servlet.form.render.IFormFieldRenderer;
import com.redshape.validators.IValidator;
import com.redshape.validators.result.IValidationResult;

public abstract class AbstractField<T> extends AbstractFormItem implements IFormField<T> {
	private static final long serialVersionUID = 5498825562953448526L;
	
	private String label;
	private IFormFieldRenderer<?> renderer;
	private T value;
	private Collection<IValidator<T, IValidationResult>> validators 
									= new HashSet<IValidator<T, IValidationResult>>();
	
	protected AbstractField() {
		this(null);
	}

	public AbstractField( String id ) {
		this(id, id);
	}
	
	protected AbstractField( String id, String name ) {
		super(id, name);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public void setRenderer( IFormFieldRenderer renderer) {
		this.renderer = renderer;
	}

	@Override
	public void setLabel(String label) {
		this.label = label;
	}

	@Override
	public String getLabel() {
		return this.label;
	}

	@Override
	public void setValue(T value) {
		this.value = value;
	}

	@Override
	public T getValue() {
		return this.value;
	}

	@SuppressWarnings("rawtypes")
	protected IFormFieldRenderer getRenderer() {
		return this.renderer;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String render() {
		if ( this.getRenderer() == null ) {
			throw new IllegalArgumentException("<null>");
		}
		
		return this.getRenderer().render(this);
	}
	
	@Override
	public void addValidator(IValidator<T, IValidationResult> validator) {
		this.validators.add(validator);
	}

	@Override
	public void addValidators(
			Collection<IValidator<T, IValidationResult>> validators) {
		this.validators.addAll( validators );
	}

	@Override
	public void clearValidators() {
		this.validators.clear();
	}

	@Override
	public void removeValidator(IValidator<T, IValidationResult> validator) {
		this.validators.remove(validator);
	}

	@Override
	public boolean isValid() {
		boolean result = true;
		for ( IValidator<T, IValidationResult> validator : this.validators ) {
			result = result && validator.isValid( this.getValue() );
		}
		
		return result;
	}
	
}
