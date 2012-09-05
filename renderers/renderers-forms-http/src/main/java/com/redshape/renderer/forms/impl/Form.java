package com.redshape.renderer.forms.impl;

import com.redshape.renderer.forms.decorators.ErrorsDecorator;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.renderer.forms.impl
 * @date 2/15/12 {6:55 PM}
 */
public class Form extends com.redshape.form.impl.Form {
    
    public Form() {
        this(null);
    }

    public Form(String id) {
        this(id, null);
    }

    public Form(String id, String name) {
        super(id, name);

        this.setDecorator( new ErrorsDecorator() );
    }
}
