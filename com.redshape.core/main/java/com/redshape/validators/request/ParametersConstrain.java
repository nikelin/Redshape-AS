package com.redshape.validators.request;

import com.redshape.io.protocols.vanilla.request.IApiRequest;
import com.redshape.validators.IValidator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author nikelin
 */
public class ParametersConstrain implements InvokeValidator {

    List<String> messages = new ArrayList<String>();
    Map<String, IValidator[] > validators = new HashMap<String, IValidator[]>();

    public ParametersConstrain addConstrain( String parameter, IValidator... validators ) {
        this.validators.put( parameter, validators );
        return this;
    }

    public List<String> getMessages() {
        return this.messages;
    }

    public boolean validate( IApiRequest invoke ) {
        boolean result = true;
        
        Map<String, Object> params = invoke.getParams();
        for( String key : this.validators.keySet() ) {
            if ( !params.containsKey(key) ) {
                this.messages.add( String.format( "Parameter `%s` is required but not present!", key ) );
                result = false;
            } else {
                for ( IValidator v : this.validators.get(key) ) {
                    if ( !v.isValid( params.get(key) ) ) {
                        this.messages.add( String.format("Invalid value for `%s` parameter.", key ) );
                        result = false;
                    }
                }
            }
        }

        invoke.markInvalid( !result );

        return result;
    }

}