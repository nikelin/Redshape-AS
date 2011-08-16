package com.redshape.servlet.form.impl.support;

import com.redshape.servlet.form.builders.BuildersFacade;
import com.redshape.servlet.form.fields.InputField;
import com.redshape.utils.range.IRange;
import com.redshape.utils.range.IntervalRange;
import com.redshape.utils.range.RangeBuilder;
import com.redshape.validators.impl.common.LengthValidator;
import com.redshape.validators.impl.common.NumericStringValidator;
import com.redshape.validators.impl.common.RangeValidator;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.servlet.form.impl.support
 * @date 8/16/11 8:46 PM
 */
public class TimeForm extends DateForm {
	private IRange<Integer> timeRange;

	public TimeForm() {
		super();
	}

	public TimeForm(String id) {
		this(id, null);
	}

	public TimeForm(String id, String name) {
		super(id, name);

		this.timeRange = RangeBuilder.createInterval( IntervalRange.Type.INCLUSIVE, 1, 60 );
	}

	@Override
	protected void buildForm() {
		super.buildForm();

		this.addField(
				BuildersFacade.newFieldBuilder()
						.withValidator( new NumericStringValidator() )
						.withValidator( new LengthValidator( 0, 2) )
						.withValidator( new RangeValidator( this.timeRange ) )
						.withName( "hour" )
						.asFieldBuilder()
						.newInputField( InputField.Type.TEXT )
		);

		this.addField(
			BuildersFacade.newFieldBuilder()
					.withValidator( new NumericStringValidator() )
					.withValidator( new LengthValidator( 0, 2 ) )
					.withValidator( new RangeValidator( this.timeRange ) )
					.withName("minute")
				.asFieldBuilder()
				.newInputField( InputField.Type.TEXT )
		);

		this.addField(
			BuildersFacade.newFieldBuilder()
					.withValidator( new NumericStringValidator() )
					.withValidator( new LengthValidator( 0, 2 ) )
					.withValidator( new RangeValidator( timeRange ) )
					.withName( "second")
				.asFieldBuilder()
				.newInputField( InputField.Type.TEXT )
		);
	}
}
