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
