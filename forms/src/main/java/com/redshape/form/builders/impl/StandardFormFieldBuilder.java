package com.redshape.form.builders.impl;

import com.redshape.form.IFormField;
import com.redshape.form.builders.AbstractFormItemBuilder;
import com.redshape.form.builders.IFormFieldBuilder;
import com.redshape.form.data.IFieldDataProvider;
import com.redshape.form.decorators.DecoratorAttribute;
import com.redshape.form.decorators.IDecorator;
import com.redshape.form.fields.*;
import com.redshape.utils.Commons;
import com.redshape.utils.validators.IValidator;
import com.redshape.utils.validators.result.IValidationResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class StandardFormFieldBuilder extends AbstractFormItemBuilder
        implements IFormFieldBuilder {

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
        return field;
    }

    @Override
    public CheckboxField newCheckboxField() {
        CheckboxField field = new CheckboxField();
        this.processField(field);
        return field;
    }

    @Override
    public InputField newInputField( InputField.Type type ) {
        InputField field = new InputField(type);
        this.processField(field);
        return field;
    }

    @Override
    public <T> SelectField<T> newSelectField(IFieldDataProvider<T> dataProvider ) {
        SelectField<T> field = new SelectField<T>();
        this.processField(field);
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
        field.addOptions(options);
        return field;
    }

    @Override
    public TextAreaField newTextAreaField() {
        TextAreaField field = new TextAreaField();
        this.processField(field);
        return field;
    }

    @Override
    public <T> RadioGroupField<T> newRadioGroupField(IFieldDataProvider<T> dataProvider, T selected) {
        RadioGroupField<T> field = new RadioGroupField<T>();
        this.processField(field);
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

}
