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
