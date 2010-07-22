package com.vio.validators.request;

import com.vio.io.protocols.vanilla.request.InterfaceInvocation;
import com.vio.validators.Validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author nikelin
 */
public class ParametersConstrain implements InvokeValidator {

    List<String> messages = new ArrayList<String>();
    Map<String, Validator[] > validators = new HashMap<String, Validator[]>();

    public ParametersConstrain addConstrain( String parameter, Validator... validators ) {
        this.validators.put( parameter, validators );
        return this;
    }

    public List<String> getMessages() {
        return this.messages;
    }

    public boolean validate( InterfaceInvocation invoke ) {
        boolean result = true;
        
        Map<String, Object> params = invoke.getParams();
        for( String key : this.validators.keySet() ) {
            if ( !params.containsKey(key) ) {
                this.messages.add( String.format( "Parameter `%s` is required but not present!", key ) );
                result = false;
            } else {
                for ( Validator v : this.validators.get(key) ) {
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