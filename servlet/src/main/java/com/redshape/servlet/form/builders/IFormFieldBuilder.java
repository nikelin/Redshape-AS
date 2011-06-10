package com.redshape.servlet.form.builders;

import java.util.List;
import java.util.Map;

import com.redshape.servlet.form.fields.CheckboxField;
import com.redshape.servlet.form.fields.CheckboxGroupField;
import com.redshape.servlet.form.fields.RadioGroupField;
import com.redshape.servlet.form.fields.SelectField;
import com.redshape.servlet.form.fields.InputField;

/**
 * Common implementation for a form fields builder.
 *  
 * @author nikelin
 */
public interface IFormFieldBuilder extends IFormItemBuilder {
	
	public IFormFieldBuilder withValue( Object value );
	
	public IFormFieldBuilder withLabel( String label );
	
	/**
	 * Create new radio buttons box with a given parameters as initial state.
	 * 
	 * @param <T>
	 * @param id
	 * @param name
	 * @param values
	 * @return
	 */
	public <T> RadioGroupField<T> newRadioGroupField( Map<String, T> values );
	
	/**
	 * Create new radio buttons box with a given parameters as initial state.
	 * @param <T>
	 * @param id
	 * @param name
	 * @param values
	 * @param selected Value selected by default
	 * @return
	 */
	public <T> RadioGroupField<T> newRadioGroupField( Map<String, T> values,
													  T selected );
	
	/**
	 * Create new checkbox with a given parameters as initial state
	 * @param id
	 * @param name
	 * @param value Indicated initial state of checkbox ( selected / unselected )
	 * @return
	 */
	public CheckboxField newCheckboxField();
	
	/**
	 * Build text field with a given parameters as initial state
	 *  
	 * @param type
	 * @param label
	 * @param id
	 * @return
	 */
	public InputField newInputField( InputField.Type type );
	
	/**
	 * Construct new <select> field from a given parameters as initial state
	 * @param <T>
	 * @param id
	 * @param name
	 * @param names
	 * @Param values
	 * @return
	 */
	public <T> SelectField<T> newSelectField( String[] names,  T[] values );

	/**
	 * Construct new <select> field from a given parameters as initial state
	 * @param <T>
	 * @param id
	 * @param name
	 * @param options
	 * @return
	 */
	public <T> SelectField<T> newSelectField( Map<String, T> options );
	
	/**
	 * Create group of checkboxes from a given parameters as initial state
	 * @param <T>
	 * @param id
	 * @param name
	 * @param names
	 * @param values
	 * @return
	 */
	public <T> CheckboxGroupField<T> newCheckboxGroupField( String[] names, T[] values );
	
	/**
	 * Create group of checkboxes from a given parameters as initial state
	 * @param <T>
	 * @param id
	 * @param name
	 * @param options
	 * @return
	 */
	public <T> CheckboxGroupField<T> newCheckboxGroupField( Map<String, T> names,
												List<T> values );
	
	/**
	 * Create group of checkboxes from a given parameters as initial state
	 * @param <T>
	 * @param id
	 * @param name
	 * @param options
	 * @param values
	 * @return
	 */
	public <T> CheckboxGroupField<T> newCheckboxGroupField( Map<String, T> options,
												T[] values );
	
	/**
	 * Create group of checkboxes from a given parameters as initial state
	 * @param <T>
	 * @param id
	 * @param name
	 * @param values
	 * @param selected
	 * @return
	 */
	public <T> CheckboxGroupField<T> newCheckboxGroupField( String[] names,
												T[] values, 
												T[] selected );
	
}
