package com.redshape.validators;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @author nikelin
 */
public class NotEmpty implements Validator {

    public boolean isValid( String object ) {
        return !object.isEmpty();
    }

    public boolean isValid( List<?> object ) {
        return !object.isEmpty();
    }

    public boolean isValid( Collection<?> object ) {
        return !object.isEmpty();
    }

    public boolean isValid( Set<?> object ) {
        return !object.isEmpty();
    }

    public boolean isValid( Object object ) {
        return object != null;
    }
}