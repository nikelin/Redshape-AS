package com.redshape.servlet.form.builders.impl;

import com.redshape.servlet.form.IFormField;
import com.redshape.servlet.form.builders.IFormBuilder;
import com.redshape.servlet.form.builders.IFormFieldBuilder;
import com.redshape.servlet.form.builders.IFormItemBuilder;
import com.redshape.servlet.form.decorators.ErrorsDecorator;
import com.redshape.servlet.form.decorators.IDecorator;
import com.redshape.servlet.form.fields.*;
import com.redshape.servlet.form.render.impl.fields.*;
import com.redshape.utils.Commons;
import com.redshape.validators.IValidator;
import com.redshape.validators.result.IValidationResult;

import java.util.*;

public class StandardFormFieldBuilder implements IFormFieldBuilder {

	private Collection<IDecorator> decorators = new HashSet<IDecorator>();
	private List<IValidator<?, ?>> validators
							= new ArrayList<IValidator<?, ?>>();
	private Map<String, Object> attributes = new HashMap<String, Object>();
	private String id;
	private String name;
	private String label;
	private Object value;
    private boolean required;

    @Override
    public IFormFieldBuilder withRequired(boolean required) {
        this.required = required;
        return this;
    }

    @Override
    public <T> IFormFieldBuilder withValidators(IValidator<T, ?>[] validators) {
        this.validators.addAll( Arrays.asList(validators) );
        return this;
    }

    @Override
	public <T> IFormFieldBuilder withValidator(
			IValidator<T, ?> validator) {
		this.validators.add( validator );
		return this;
	}

	@Override
	public IFormFieldBuilder withLabel( String label ) {
		this.label = label;
		return this;
	}
	
	@Override
	public IFormFieldBuilder withValue( Object value ) {
		this.value = value;
		return this;
	}
	
	@Override
	public IFormFieldBuilder withName( String name ) {
		this.name = name;
		return this;
	}
	
	@Override
	public IFormItemBuilder withId(String id) {
		this.id = id;
		return this;
	}

	@Override
	public IFormItemBuilder withEmptyDecorators() {
		this.decorators.clear();
		return this;
	}
	
	@Override
	public IFormItemBuilder withDecorator(IDecorator decorator) {
		this.decorators.add( decorator );
		return this;
	}

	@Override
	public IFormItemBuilder withDecorators(Collection<IDecorator> decorators) {
		this.decorators.addAll(decorators);
		return this;
	}

	@Override
	public IFormItemBuilder withAttribute(String name, Object value) {
		this.attributes.put(name, value);
		return this;
	}

	@Override
	public IFormBuilder asFormBuilder() {
		throw new UnsupportedOperationException("Operation not supported");
	}

	@Override
	public IFormFieldBuilder asFieldBuilder() {
		return this;
	}

    @Override
    public LabelField newLabelField() {
        LabelField field = new LabelField();
        this.processField(field);
        field.setRenderer( new LabelFieldRenderer() );
        return field;
    }

	@Override
	public CheckboxField newCheckboxField() {
		CheckboxField field = new CheckboxField();
		this.processField(field);
		field.setRenderer( new CheckboxFieldRenderer() );
		return field;
	}
	
	@Override
	public InputField newInputField( InputField.Type type ) {
		InputField field = new InputField(type);
		this.processField(field);
		field.setRenderer( new InputFieldRenderer() );
		return field;
	}

	@Override
	public <T> SelectField<T> newSelectField( String[] names, T[] values ) { 
		return this.newSelectField( Commons.map( names, values ) );
	}

	@Override
	public <T> SelectField<T> newSelectField( Map<String, T> options) {
		SelectField<T> field = new SelectField<T>();
		this.processField(field);
		field.setRenderer( new SelectFieldRenderer() );
		field.addOptions(options);
		return field;
	}

    @Override
    public TextAreaField newTextAreaField() {
        TextAreaField field = new TextAreaField();
        this.processField(field);
        field.setRenderer( new TextAreaRenderer() );
        return field;
    }

	@Override
	public <T> RadioGroupField<T> newRadioGroupField( Map<String, T> values) {
		return this.newRadioGroupField( values, null);
	}

	@Override
	public <T> RadioGroupField<T> newRadioGroupField(Map<String, T> values, T selected) {
		RadioGroupField<T> field = new RadioGroupField<T>();
		this.processField(field);
        field.setRenderer( new RadioGroupFieldRenderer() );
		field.addOptions(values);
		field.setValue(selected);
		return field;
	}

	@Override
	public <T> CheckboxGroupField<T> newCheckboxGroupField( String[] names, T[] values) {
		return this.newCheckboxGroupField(  Commons.map(names, values), new ArrayList<T>() );
	}

	@Override
	public <T> CheckboxGroupField<T> newCheckboxGroupField( Map<String, T> names, List<T> values) {
		CheckboxGroupField<T> field = new CheckboxGroupField<T>();
        field.setRenderer( new CheckboxGroupFieldRenderer() );
		this.processField(field);
		field.addOptions(names);
		field.setValues( values );
		return field;
	}

	@Override
	public <T> CheckboxGroupField<T> newCheckboxGroupField(Map<String, T> options, T[] values) {
		return this.newCheckboxGroupField( options, Arrays.asList(values) );
	}

	@Override
	public <T> CheckboxGroupField<T> newCheckboxGroupField(String[] names, T[] values, T[] selected) {
		return this.newCheckboxGroupField( Commons.map(names, values), selected );
	}
	
	@SuppressWarnings("unchecked")
	protected <T> void processField( IFormField<T> field ) {
		if ( this.value != null ) {
			field.setValue( (T) this.value );
		}
		
		field.setId( this.id );
        field.setRequired( this.required );
		field.clearValidators();

		for ( IValidator<?, ?> validator : this.validators ) {
			field.addValidator( (IValidator<T, IValidationResult>) validator );
		}

        for ( String attribute : this.attributes.keySet() ) {
            field.setAttribute( attribute, this.attributes.get(attribute) );
        }
		
		field.clearDecorators();
		field.setDecorator( new ErrorsDecorator() );
		field.setDecorators( this.decorators.toArray( new IDecorator[ this.decorators.size() ] ) );
		field.setLabel( this.label );
		field.setName( this.name );
	}
	
}
