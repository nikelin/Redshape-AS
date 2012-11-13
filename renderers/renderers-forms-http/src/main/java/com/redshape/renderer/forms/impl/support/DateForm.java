/*
 * Copyright 2012 Cyril A. Karpenko
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.redshape.renderer.forms.impl.support;

import com.redshape.form.builders.BuildersFacade;
import com.redshape.form.fields.InputField;
import com.redshape.form.impl.Form;
import com.redshape.utils.Commons;
import com.redshape.utils.range.IntervalRange;
import com.redshape.utils.range.RangeBuilder;
import com.redshape.utils.validators.simple.NumericStringValidator;
import com.redshape.utils.validators.simple.RangeValidator;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.renderer.forms.impl.support
 * @date 8/15/11 12:41 PM
 */
public class DateForm extends Form {
	public static final String YEAR = "year";
	public static final String MONTH = "month";
	public static final String DAY = "day";

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
					.withName(DAY)
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
					.withName(MONTH)
					.withAttribute("class", "month-element")
				.asFieldBuilder()
				.newSelectField(this.createMonthsList())
		);

		this.addField(
				BuildersFacade.newFieldBuilder()
					.withValue( Calendar.getInstance().get( Calendar.YEAR ) )
					.withValidator(new NumericStringValidator())
					.withName(YEAR)
					.withAttribute("class", "year-element")
				.asFieldBuilder()
				.newInputField(InputField.Type.TEXT)
		);
	}

	protected Map<String, Object> createMonthsList() {
		Map<String, Object> result = new LinkedHashMap<String, Object>();

		Calendar calendar = Calendar.getInstance();
		for ( int i = 0; i <= 11; i++ ) {
			calendar.set(Calendar.MONTH, i);
			result.put( calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()), i );
		}

		return result;
	}

	public void setMonth( Integer value ) {
		this.setValue(MONTH, Commons.select(value, Calendar.getInstance().get(Calendar.MONTH) ) );
	}

	public Integer getMonth() {
		try {
			if ( !this.hasValue(MONTH) ) {
				return Calendar.getInstance().get( Calendar.MONTH );
			}

			return Integer.valueOf( monthFormatter.format( monthFormatter.parse(
					this.<String>getValue(MONTH))));
		} catch ( ParseException e ) {
			throw new IllegalArgumentException( e.getMessage(), e );
		}
	}

	public void setDay( Integer value ) {
		this.setValue(DAY, Commons.select( value, Calendar.getInstance().get(Calendar.DAY_OF_MONTH) ) );
	}

	public Integer getDay() {
		try {
			if ( !this.hasValue(DAY) ) {
				return Calendar.getInstance().get( Calendar.DAY_OF_MONTH );
			}

			return Integer.valueOf( dayFormatter.format(
					dayFormatter.parse(this.<String>getValue(DAY) ) ) );
		} catch ( ParseException e ) {
			throw new IllegalArgumentException( e.getMessage(), e );
		}
	}

	public void setYear( Integer value ) {
		this.setValue( YEAR, Commons.select( value, Calendar.getInstance().get(Calendar.YEAR) ) );
	}

	public Integer getYear() {
		try {
			if ( !this.hasValue(YEAR) ) {
				return Calendar.getInstance().get( Calendar.YEAR );
			}

			return Integer.valueOf(
				yearFormatter.format(
					yearFormatter.parse(this.<String>getValue(YEAR) ) ) );
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
