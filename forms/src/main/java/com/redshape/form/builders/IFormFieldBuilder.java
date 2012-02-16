package com.redshape.form.builders;

import com.redshape.form.data.IFieldDataProvider;
import com.redshape.form.fields.*;
import com.redshape.utils.validators.IValidator;

import java.util.List;
import java.util.Map;

/**
 * Common implementation for a form fields builder.
 *  
 * @author nikelin
 */
public interface IFormFieldBuilder extends IFormItemBuilder {

    /**
     * Make field required to be present is income request
     * @param required
     * @return
     */
    public IFormFieldBuilder withRequired( boolean required );

    /**
     * Add to build profile specified validators set
     * @param validators
     * @return
     */
    public IFormFieldBuilder withValidators( IValidator<?,?>[] validators );

	/**
	 * Add to build profile specified validator
	 * @param validator
	 * @return
	 */
	public IFormFieldBuilder withValidator( IValidator<?,?> validator );
	
	/**
	 * Set new field value to build profile
	 * @param value
	 * @return
	 */
	public IFormFieldBuilder withValue( Object value );

    /**
     * Apply label to the constructable field.
     * @param label
     * @return
     */
	public IFormFieldBuilder withLabel( String label );

    /**
     * Creates new label field with value as a text
     * @return
     */
    public LabelField newLabelField();

	/**
	 * Create new radio buttons box with a given parameters as initial state.
	 * 
	 * @param <T>
	 * @param values
	 * @return
	 */
	public <T> RadioGroupField<T> newRadioGroupField( Map<String, T> values );
	
	/**
	 * Create new radio buttons box with a given parameters as initial state.
	 * @param <T>
	 * @param values
	 * @param selected Value selected by default
	 * @return
	 */
	public <T> RadioGroupField<T> newRadioGroupField( Map<String, T> values,
													  T selected );

	public <T> RadioGroupField<T> newRadioGroupField( IFieldDataProvider<T> dataProvider );

	public <T> RadioGroupField<T> newRadioGroupField( IFieldDataProvider<T> dataProvider,
													  T selected );

    /**
     * Creates new textarea field
     *
     * @return
     */
    public TextAreaField newTextAreaField();

	/**
	 * Create new checkbox with a given parameters as initial state
	 * @return
	 */
	public CheckboxField newCheckboxField();
	
	/**
	 * Build text field with a given parameters as initial state
	 *  
	 * @param type
	 * @return
	 */
	public InputField newInputField( InputField.Type type );
	
	/**
	 * Construct new <select> field from a given parameters as initial state
	 * @param <T>
	 * @param names
	 * @Param values
	 * @return
	 */
	public <T> SelectField<T> newSelectField( String[] names,  T[] values );

	/**
	 * Construct new <select> field from a given parameters as initial state
	 * @param <T>
	 * @param options
	 * @return
	 */
	public <T> SelectField<T> newSelectField( Map<String, T> options );

	public <T> SelectField<T> newSelectField( IFieldDataProvider<T> dataProvider );

	/**
	 * Create group of checkboxes from a given parameters as initial state
	 * @param <T>
	 * @param names
	 * @param values
	 * @return
	 */
	public <T> CheckboxGroupField<T> newCheckboxGroupField( String[] names, T[] values );

	public <T> CheckboxGroupField<T> newCheckboxGroupField( IFieldDataProvider<T> dataProvider );

	public <T> CheckboxGroupField<T> newCheckboxGroupField( IFieldDataProvider<T> dataProvider, List<T> values );
	
	/**
	 * Create group of checkboxes from a given parameters as initial state
	 * @param <T>
     * @param names
     * @param values
	 * @return
	 */
	public <T> CheckboxGroupField<T> newCheckboxGroupField( Map<String, T> names,
												List<T> values );

	/**
	 * Create group of checkboxes without initial state
	 *
	 * @param names
	 */
	public <T> CheckboxGroupField<T> newCheckboxGroupField( Map<String, T> names );

	/**
	 * Create group of checkboxes from a given parameters as initial state
	 * @param <T>
	 * @param options
	 * @param values
	 * @return
	 */
	public <T> CheckboxGroupField<T> newCheckboxGroupField( Map<String, T> options,
												T[] values );
	
	/**
	 * Create group of checkboxes from a given parameters as initial state
	 * @param <T>
     * @param names
	 * @param values
	 * @param selected
	 * @return
	 */
	public <T> CheckboxGroupField<T> newCheckboxGroupField( String[] names,
												T[] values, 
												T[] selected );
	
}
