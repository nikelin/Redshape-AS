package com.redshape.validators.common;

import com.redshape.bindings.BindingException;
import com.redshape.bindings.IBeanInfo;
import com.redshape.bindings.accessors.AccessException;
import com.redshape.bindings.types.IBindable;
import com.redshape.utils.AnnotatedObject;
import com.redshape.validators.IValidator;
import com.redshape.validators.ValidatorsFacade;
import com.redshape.validators.result.IResultsList;
import com.redshape.validators.result.IValidationResult;
import com.redshape.validators.result.ResultsList;
import com.redshape.validators.result.ValidationResult;

import java.lang.annotation.Annotation;

/**
 * @author nikelin
 * @date 18/04/11
 * @package com.redshape.validators
 */
public class BeansValidator implements IValidator<Object, IResultsList> {
	private IBeanInfo beanInfo;

	public BeansValidator( IBeanInfo info ) {
		this.beanInfo = info;
	}

	protected IBeanInfo getBeanInfo() {
		return this.beanInfo;
	}

	@Override
	public boolean isValid(Object value) {
		return this.validate( value ).isValid();
	}

	@Override
	public IResultsList validate(Object value) {
		try {
			ResultsList result = new ResultsList();

			for ( IBindable bindable : this.getBeanInfo().getBindables() ) {
			   result.add( this.proccessMember( value, bindable) );
			}

			return result;
		} catch ( BindingException e ) {
			return new ResultsList(false);
		}
	}

	protected IValidationResult proccessMember( Object value, IBindable bindable ) throws AccessException {
		for (Annotation annotation : bindable.getAnnotations() ) {
			if ( !this.isValid( value, bindable, annotation ) ) {
				return new ValidationResult( bindable.getId(), annotation, false );
			}
		}

		return new ValidationResult( bindable.getId() );
	}

	protected boolean isValid( Object value, IBindable bindable, Annotation annotation ) throws AccessException {
		IValidator<AnnotatedObject,?> validator = ValidatorsFacade.getInstance().getAnnotationValidator( annotation.annotationType() );
		if ( validator == null ) {
			return true;
		}

		return validator.isValid( new AnnotatedObject( bindable.<Object>read(value), annotation ) );
	}
}
