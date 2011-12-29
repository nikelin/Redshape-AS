package com.redshape.servlet.form.builders.impl;

import com.redshape.servlet.form.IFormField;
import com.redshape.servlet.form.builders.AbstractFormItemBuilder;
import com.redshape.servlet.form.builders.IFormFieldBuilder;
import com.redshape.servlet.form.data.IFieldDataProvider;
import com.redshape.servlet.form.decorators.DecoratorAttribute;
import com.redshape.servlet.form.decorators.IDecorator;
import com.redshape.servlet.form.fields.*;
import com.redshape.servlet.form.render.IFormFieldRenderer;
import com.redshape.servlet.form.render.impl.fields.*;
import com.redshape.servlet.form.render.registry.StandardFormItemRenderersRegistry;
import com.redshape.utils.Commons;
import com.redshape.validators.IValidator;
import com.redshape.validators.result.IValidationResult;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class StandardFormFieldBuilder extends AbstractFormItemBuilder
									  implements IFormFieldBuilder {
    private static final Logger log = Logger.getLogger( StandardFormFieldBuilder.class );

	private List<IValidator<?, ?>> validators
							= new ArrayList<IValidator<?, ?>>();
	private String label;
	private Object value;
    private boolean required;

    @Override
    public IFormFieldBuilder withRequired(boolean required) {
        this.required = required;
        return this;
    }

    @Override
    public IFormFieldBuilder withValidators(IValidator<?,?>[] validators) {
        this.validators.addAll( Arrays.asList(validators) );
        return this;
    }

    @Override
	public IFormFieldBuilder withValidator( IValidator<?,?> validator) {
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
    public LabelField newLabelField() {
        LabelField field = new LabelField();
        this.processField(field);
        field.setRenderer( this.getRenderer(LabelField.class, LabelFieldRenderer.class ) );
        return field;
    }

	@Override
	public CheckboxField newCheckboxField() {
		CheckboxField field = new CheckboxField();
		this.processField(field);
		field.setRenderer( this.getRenderer(CheckboxField.class, CheckboxFieldRenderer.class ));
		return field;
	}
	
	@Override
	public InputField newInputField( InputField.Type type ) {
		InputField field = new InputField(type);
		this.processField(field);
        field.setRenderer( this.getRenderer( InputField.class, InputFieldRenderer.class ) );

		return field;
	}

	@Override
	public <T> SelectField<T> newSelectField(IFieldDataProvider<T> dataProvider ) {
		SelectField<T> field = new SelectField<T>();
		this.processField(field);
		field.setRenderer( this.getRenderer(SelectField.class, SelectFieldRenderer.class ) );
		field.setDataProvider(dataProvider);
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
		field.setRenderer( this.getRenderer(SelectField.class, SelectFieldRenderer.class ) );
		field.addOptions(options);
		return field;
	}

    @Override
    public TextAreaField newTextAreaField() {
        TextAreaField field = new TextAreaField();
        this.processField(field);
        field.setRenderer( this.getRenderer(TextAreaField.class, TextAreaRenderer.class) );
        return field;
    }

	@Override
	public <T> RadioGroupField<T> newRadioGroupField(IFieldDataProvider<T> dataProvider, T selected) {
		RadioGroupField<T> field = new RadioGroupField<T>();
		this.processField(field);
        field.setRenderer( this.getRenderer(RadioGroupField.class, RadioGroupFieldRenderer.class) );
		field.setDataProvider(dataProvider);
		field.setValue(selected);
		return field;
	}

	@Override
	public <T> RadioGroupField<T> newRadioGroupField(IFieldDataProvider<T> dataProvider) {
		return this.newRadioGroupField(dataProvider, null );
	}

	@Override
	public <T> RadioGroupField<T> newRadioGroupField( Map<String, T> values) {
		return this.newRadioGroupField( values, null);
	}

	@Override
	public <T> RadioGroupField<T> newRadioGroupField(Map<String, T> values, T selected) {
		RadioGroupField<T> field = new RadioGroupField<T>();
		this.processField(field);
        field.setRenderer( this.getRenderer(RadioGroupField.class, RadioGroupFieldRenderer.class) );
		field.addOptions(values);
		field.setValue(selected);
		return field;
	}

	@Override
	public <T> CheckboxGroupField<T> newCheckboxGroupField( String[] names, T[] values) {
		return this.newCheckboxGroupField(  Commons.map(names, values), new ArrayList<T>() );
	}

	@Override
	public <T> CheckboxGroupField<T> newCheckboxGroupField(Map<String, T> names) {
		return this.newCheckboxGroupField( names, new ArrayList<T>() );
	}

	@Override
	public <T> CheckboxGroupField<T> newCheckboxGroupField(IFieldDataProvider<T> dataProvider, List<T> values) {
		CheckboxGroupField<T> field = new CheckboxGroupField<T>();
        field.setRenderer( this.getRenderer(CheckboxGroupField.class, CheckboxGroupFieldRenderer.class));
		this.processField(field);
		field.setDataProvider(dataProvider);
		field.setValues( values );
		return field;
	}

	@Override
	public <T> CheckboxGroupField<T> newCheckboxGroupField(IFieldDataProvider<T> dataProvider) {
		return this.newCheckboxGroupField(dataProvider, new ArrayList<T>() );
	}

	@Override
	public <T> CheckboxGroupField<T> newCheckboxGroupField( Map<String, T> names, List<T> values) {
		CheckboxGroupField<T> field = new CheckboxGroupField<T>();
        field.setRenderer( this.getRenderer( CheckboxGroupField.class, CheckboxGroupFieldRenderer.class ) );
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

        if ( !this.decorators.isEmpty() ) {
			for ( IDecorator decorator : this.decorators ) {
				for ( DecoratorAttribute attribute : this.decoratorAttributes.keySet() ) {
					if ( decorator.isSupported(attribute) ) {
						decorator.setAttribute( attribute, this.decoratorAttributes.get(attribute) );
					}
				}

				field.setDecorator(decorator);
			}
        }

		field.setLabel( this.label );
		field.setName( this.name );
	}
	
    protected <T extends IFormField<?>> IFormFieldRenderer<T> getRenderer( Class<T> fieldClazz,
                                                       Class<? extends IFormFieldRenderer<T>> defaultRendererClass ) {
        IFormFieldRenderer<T> renderer = StandardFormItemRenderersRegistry.getDefault().getRenderer(fieldClazz);
        if ( renderer != null ) {
            return renderer;
        }

        try {
            return defaultRendererClass.newInstance();
        } catch ( Throwable e ) {
            log.error( e.getMessage(), e );
            return null;
        }
    }
    
}
