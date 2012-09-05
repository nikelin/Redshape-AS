package com.redshape.form.fields;

import com.redshape.form.AbstractFormItem;
import com.redshape.form.IFormField;
import com.redshape.utils.validators.IValidator;
import com.redshape.utils.validators.result.IValidationResult;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class AbstractField<T> extends AbstractFormItem implements IFormField<T> {
	private static final long serialVersionUID = 5498825562953448526L;
	
	private String label;
	private List<IValidationResult> validationResults = new ArrayList<IValidationResult>();
	private T value;
	private List<IValidator<T, IValidationResult>> validators
									= new ArrayList<IValidator<T, IValidationResult>>();
    private boolean required;

	protected AbstractField() {
		this(null);
	}

	public AbstractField( String id ) {
		this(id, id);
	}
	
	protected AbstractField( String id, String name ) {
		super(id, name);
	}

	@Override
	public boolean hasMultiValues() {
		return false;
	}

	@Override
	public List<T> getValues() {
		throw new IllegalArgumentException("Not supported by the current field!");
	}

	@Override
    public boolean isRequired() {
        return this.required;
    }

    @Override
    public void setRequired(boolean value) {
        this.required = value;
    }

    @Override
	public Collection<IValidationResult> getValidationResults() {
		return validationResults;
	}

    @Override
    public void resetMessages() {
		if ( this.hasAttribute("class") ) {
			String classAttribute = this.getAttribute("class");
			String[] classParts = classAttribute.split("\\s");
			for ( String part : classParts ) {
				if ( !part.trim().equals("error") ) {
					continue;
				}

				classAttribute = classAttribute.replace(part, "");
			}

			this.setAttribute("class", classAttribute);
		}

        this.validationResults.clear();
    }

    @Override
    public void resetState() {
        this.setValue(null);
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
	
	@Override
	public <V extends IValidator<T, IValidationResult>> void addValidator(V validator) {
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
        this.resetMessages();

		boolean result = true;
		for ( IValidator<T, IValidationResult> validator : this.validators ) {
			IValidationResult validationResult = validator.validate( this.getValue() );
			boolean validatorResult = validationResult.isValid();
            result = result && validatorResult;
			this.validationResults.add( validationResult );
		}
		
		return result;
	}

}
