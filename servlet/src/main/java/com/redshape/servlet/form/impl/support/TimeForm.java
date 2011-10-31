package com.redshape.servlet.form.impl.support;

import com.redshape.servlet.form.builders.BuildersFacade;
import com.redshape.servlet.form.fields.InputField;
import com.redshape.utils.Commons;
import com.redshape.utils.range.IRange;
import com.redshape.utils.range.IntervalRange;
import com.redshape.utils.range.RangeBuilder;
import com.redshape.validators.impl.common.LengthValidator;
import com.redshape.validators.impl.common.NumericStringValidator;
import com.redshape.validators.impl.common.RangeValidator;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.servlet.form.impl.support
 * @date 8/16/11 8:46 PM
 */
public class TimeForm extends DateForm {
	public static final String HOUR = "hour";
	public static final String MINUTE = "minute";
	public static final String SECOND = "second";

	private IRange<Integer> timeRange;

	private static final DateFormat timeFormatter = new SimpleDateFormat("y.M.d.H.m.s");
	private static final DateFormat hoursFormatter = new SimpleDateFormat("H");
	private static final DateFormat minutesFormatter = new SimpleDateFormat("m");
	private static final DateFormat secondsFormatter = new SimpleDateFormat("s");

	public TimeForm() {
		super();
	}

	public TimeForm(String id) {
		this(id, null);
	}

	public TimeForm(String id, String name) {
		super(id, name);
	}

	@Override
	protected void buildForm() {
		super.buildForm();

		this.timeRange = RangeBuilder.createInterval(IntervalRange.Type.INCLUSIVE, 0, 59);

		this.addField(
				BuildersFacade.newFieldBuilder()
					.withValue( Calendar.getInstance().get(Calendar.HOUR_OF_DAY) )
					.withValidator(new NumericStringValidator())
					.withValidator(new LengthValidator(0, 2))
					.withValidator(new RangeValidator(this.timeRange))
					.withName(HOUR)
					.withAttribute("class", "hour-element")
				.asFieldBuilder()
				.newInputField(InputField.Type.TEXT)
		);

		this.addField(
			BuildersFacade.newFieldBuilder()
					.withValue( Calendar.getInstance().get(Calendar.MINUTE) )
					.withValidator(new NumericStringValidator())
					.withValidator( new LengthValidator( 0, 2 ) )
					.withValidator( new RangeValidator( this.timeRange ) )
					.withName(MINUTE)
					.withAttribute("class", "minute-element")
				.asFieldBuilder()
				.newInputField( InputField.Type.TEXT )
		);

		this.addField(
			BuildersFacade.newFieldBuilder()
					.withValue( Calendar.getInstance().get( Calendar.SECOND ) )
					.withValidator(new NumericStringValidator())
					.withValidator( new LengthValidator( 0, 2 ) )
					.withValidator( new RangeValidator( this.timeRange ) )
					.withName(SECOND)
					.withAttribute("class", "second-element")
				.asFieldBuilder()
				.newInputField( InputField.Type.TEXT )
		);
	}

	public void setHour( Integer hour ) {
		this.setValue(HOUR, Commons.select( hour, Calendar.getInstance().get(Calendar.HOUR_OF_DAY) ) );
	}

	public Integer getHour() {
		try {
			if ( !this.hasValue(HOUR) ) {
				return 0;
			}

			return Integer.valueOf(
					hoursFormatter.format(
							hoursFormatter.parse(this.<String>getValue(HOUR))));
		} catch ( ParseException e ) {
			throw new IllegalArgumentException( e.getMessage(), e );
		}
	}

	public void setMinute( Integer value ) {
		this.setValue(MINUTE, Commons.select( value, Calendar.getInstance().get(Calendar.MINUTE) ) );
	}

	public Integer getMinute() {
		try {
			if ( !this.hasValue(MINUTE) ) {
				return 0;
			}

			return Integer.valueOf(
				minutesFormatter.format(
					minutesFormatter.parse( this.<String>getValue(MINUTE) ) ) );
		} catch ( ParseException e ) {
			throw new IllegalArgumentException( e.getMessage(), e );
		}
	}

	public void setSecond( Integer value ) {
		this.setValue(SECOND, Commons.select(value, 0 ) );
	}

	public Integer getSecond() {
		try {
			if ( !this.hasValue(SECOND) ) {
				return 0;
			}

			return Integer.valueOf(
				secondsFormatter.format(
					secondsFormatter.parse( this.<String>getValue(SECOND) )
				)
			);
		} catch ( ParseException e ) {
			throw new IllegalArgumentException( e.getMessage(), e );
		}
	}

	public void fromDate( Date date ) {
		super.fromDate(date);

		if ( date == null ) {
			return;
		}

		this.setHour( Integer.valueOf( hoursFormatter.format(date) ) );
		this.setMinute( Integer.valueOf( minutesFormatter.format(date) ) );
		this.setSecond( Integer.valueOf( secondsFormatter.format(date) ) );
	}

	@Override
	public Date prepareDate() {
		try {
			String result = dateFormatter.format( super.prepareDate() )
			 	+ "." + this.getHour()
				+ "." + this.getMinute()
				+ "." + this.getSecond();
			return timeFormatter.parse( result );
		} catch ( ParseException e ) {
			throw new IllegalArgumentException( e.getMessage(), e );
		}
	}
}
