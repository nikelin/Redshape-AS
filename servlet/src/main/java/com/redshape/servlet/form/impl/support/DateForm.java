package com.redshape.servlet.form.impl.support;

import com.redshape.servlet.form.builders.BuildersFacade;
import com.redshape.servlet.form.fields.InputField;
import com.redshape.servlet.form.impl.Form;
import com.redshape.utils.Commons;
import com.redshape.utils.range.IntervalRange;
import com.redshape.utils.range.RangeBuilder;
import com.redshape.validators.impl.common.NumericStringValidator;
import com.redshape.validators.impl.common.RangeValidator;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.servlet.form.impl.support
 * @date 8/15/11 12:41 PM
 */
public class DateForm extends Form {
	protected static final DateFormat dateFormatter = new SimpleDateFormat("y.M.d");
	protected static final DateFormat yearFormatter = new SimpleDateFormat("y");
	protected static final DateFormat monthFormatter = new SimpleDateFormat("M");
	protected static final DateFormat dayFormatter = new SimpleDateFormat("d");

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
		this.setAttribute("class", "date-form");

		this.addField(
				BuildersFacade.newFieldBuilder()
					.withValue( Calendar.getInstance().get( Calendar.DAY_OF_MONTH ))
					.withValidator(new NumericStringValidator())
					.withValidator(new RangeValidator(
							RangeBuilder.createInterval(IntervalRange.Type.INCLUSIVE, 1, 31)
					))
					.withName("day")
					.withAttribute("class", "day-element")
				.asFieldBuilder()
				.newInputField(InputField.Type.TEXT)
		);

		this.addField(
				BuildersFacade.newFieldBuilder()
					.withValue( Calendar.getInstance().get( Calendar.MONTH ) )
					.withValidator(new RangeValidator(
							RangeBuilder.createInterval(IntervalRange.Type.INCLUSIVE, 1, 12)
					))
					.withValidator(new NumericStringValidator() )
					.withName("month")
					.withAttribute("class", "month-element")
				.asFieldBuilder()
				.newSelectField(this.createMonthsList())
		);

		this.addField(
				BuildersFacade.newFieldBuilder()
					.withValue( Calendar.getInstance().get( Calendar.YEAR ) )
					.withValidator(new NumericStringValidator())
					.withName("year")
					.withAttribute("class", "year-element")
				.asFieldBuilder()
				.newInputField(InputField.Type.TEXT)
		);
	}

	protected Map<String, Object> createMonthsList() {
		Map<String, Object> result = new LinkedHashMap<String, Object>();

		Calendar calendar = Calendar.getInstance();
		for ( int i = 1; i <= 12; i++ ) {
			calendar.set(Calendar.MONTH, i);
			result.put( calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()), i );
		}

		return result;
	}

	public void setMonth( Integer value ) {
		this.setValue("month", Commons.select(value, Calendar.getInstance().get(Calendar.MONTH) ) );
	}

	public Integer getMonth() {
		try {
			return Integer.valueOf( monthFormatter.format( monthFormatter.parse(this.<String>getValue("month"))));
		} catch ( ParseException e ) {
			throw new IllegalArgumentException( e.getMessage(), e );
		}
	}

	public void setDay( Integer value ) {
		this.setValue("day", Commons.select( value, Calendar.getInstance().get(Calendar.DAY_OF_MONTH) ) );
	}

	public Integer getDay() {
		try {
			return Integer.valueOf( dayFormatter.format( dayFormatter.parse(this.<String>getValue("day") ) ) );
		} catch ( ParseException e ) {
			throw new IllegalArgumentException( e.getMessage(), e );
		}
	}

	public void setYear( Integer value ) {
		this.setValue( "year", Commons.select( value, Calendar.getInstance().get(Calendar.YEAR) ) );
	}

	public Integer getYear() {
		try {
			return Integer.valueOf( yearFormatter.format( yearFormatter.parse(this.<String>getValue("year") ) ) );
		} catch ( ParseException e ) {
			throw new IllegalArgumentException( e.getMessage(), e );
		}
	}

	public void fromDate( Date date ) {
		if ( date == null ) {
			return;
		}

		this.setYear(Integer.valueOf(yearFormatter.format(date)));
		this.setMonth(Integer.valueOf(monthFormatter.format(date)));
		this.setDay( Integer.valueOf(dayFormatter.format(date)) );
	}

	public Date prepareDate() {
		try {
			return dateFormatter.parse( this.getYear() + "." + this.getMonth() + "." + this.getDay() );
		} catch ( ParseException e ) {
			throw new IllegalArgumentException( e.getMessage(), e );
		}
	}
}
