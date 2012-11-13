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

package com.redshape.ui.gwt.helpers.notification;

/**
 * Created with IntelliJ IDEA.
 * User: max.gura
 * Date: 23.08.12
 * Time: 20:52
 * To change this template use File | Settings | File Templates.
 */
public interface INotificationFacade {

    public void info(String message);

    public void error(String message);

    public void error(Throwable caught);

    public void error(Throwable caught, String message);

    public void alert(String message);

}