package com.redshape.renderer.forms;

import com.google.gwt.user.client.Element;
import com.redshape.form.IForm;
import com.redshape.form.IUserRequest;
import com.redshape.ui.gwt.FormUtils;

import java.util.Map;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.renderer.forms
 * @date 2/21/12 {2:35 PM}
 */
public class ClientProcessRequest implements IUserRequest {
    private IForm form;
    
    public ClientProcessRequest(IForm form) {
        super();

        this.form = form;
    }
    
    protected IForm getForm() {
        return this.form;
    }

    @Override
    public String getMethod() {
         return this.form.getMethod();
    }

    @Override
    public <T> Map<String, T> getParameters() {
        return (Map<String, T>) FormUtils.buildMap(this.form.<Element>getAttribute("domElement"));
    }

    @Override
    public String getParameter(String name) {
        return this.<String>getParameters().get(name);
    }
}
