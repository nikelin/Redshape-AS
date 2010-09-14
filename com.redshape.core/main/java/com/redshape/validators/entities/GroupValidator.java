package com.redshape.validators.entities;

import com.redshape.validators.NotEmpty;
import com.redshape.validators.Validator;
import com.redshape.validators.ValidatorsFactory;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Feb 11, 2010
 * Time: 1:37:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class GroupValidator implements Validator {

    public boolean isValid( Object object ) {
        return this.isValid( (Map<String, Object>) object );
    }

    public boolean isValid( Map<String, Object> object ) {
        boolean result = true;

        String[] fields = {"name", "description"};

        for ( String field : fields ) {
            if ( !object.containsKey(field) || ValidatorsFactory.getDefault().getValidator( NotEmpty.class ).isValid( object.get(field) ) ) {
                result = false;
                break;
            }
        }

        return result;
    }
}
