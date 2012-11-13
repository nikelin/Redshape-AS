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

package com.redshape.servlet.actions.index;

import com.redshape.servlet.core.controllers.AbstractAction;
import com.redshape.servlet.core.controllers.Action;
import com.redshape.servlet.views.ViewAttributes;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 10/11/10
 * Time: 12:52 AM
 * To change this template use File | Settings | File Templates.
 */
@Action( name = "index", controller = "index", view="index/index" )
public class IndexAction extends AbstractAction {

    @Override
    public void process() {
        this.getView().setAttribute(ViewAttributes.Title, "Home");
    }

}
