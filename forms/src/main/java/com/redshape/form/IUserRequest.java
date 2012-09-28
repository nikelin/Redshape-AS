package com.redshape.form;

import java.io.Serializable;
import java.util.Map;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.form
 * @date 2/15/12 {6:14 PM}
 */
public interface IUserRequest extends Serializable {

    public String getMethod();
    
    public <T> Map<String, T> getParameters();
    
    public String getParameter( String name );
    
}
