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

package com.redshape.servlet.resources;

import com.redshape.servlet.resources.types.Link;
import com.redshape.servlet.resources.types.Script;
import com.redshape.servlet.resources.types.Style;

import java.io.Serializable;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 17.06.11
 * Time: 14:11
 * To change this template use File | Settings | File Templates.
 */
public interface IWebResourceWriter extends Serializable {

    public String writeScripts( List<Script> scripts );

    public String writeStyles( List<Style> styles );

    public String writeLinks( List<Link> links );

}
