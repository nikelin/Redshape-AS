package com.redshape.servlet.form.impl.support;

import com.redshape.servlet.form.builders.BuildersFacade;
import com.redshape.servlet.form.fields.InputField;
import com.redshape.servlet.form.impl.Form;
import com.redshape.validators.impl.common.NumericStringValidator;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.servlet.form.impl.support
 * @date 8/15/11 12:41 PM
 */
public class DateForm extends Form {

	public DateForm() {
		this(null);
	}

	public DateForm(String id) {
		this(id, null);
	}

	public DateForm(String id, String name) {
		super(id, name);

		this.buildForm();
	}

	protected void buildForm() {
		this.addField(
			BuildersFacade.newFieldBuilder()
					.withValidator(new NumericStringValidator())
					.withName("day")
				.asFieldBuilder()
				.newInputField(InputField.Type.TEXT)
		);

		this.addField(
			BuildersFacade.newFieldBuilder()
					.withName("month")
				.asFieldBuilder()
				.newSelectField( this.createMonthsList() )
		);

		this.addField(
			BuildersFacade.newFieldBuilder()
					.withName("year")
				.asFieldBuilder()
				.newInputField( InputField.Type.TEXT )
		);
	}

	protected Map<String, Object> createMonthsList() {
		Map<String, Object> result = new HashMap<String, Object>();

		Calendar calendar = Calendar.getInstance();
		for ( int i = 1; i <= 12; i++ ) {
			calendar.set( Calendar.MONTH, i );
			result.put( calendar.getDisplayName( Calendar.MONTH, Calendar.LONG, Locale.getDefault() ), i );
		}

		return result;
	}
}
