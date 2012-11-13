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
 * User: nakham
 * Date: 30.08.12
 * Time: 17:09
 * To change this template use File | Settings | File Templates.
 */
public class Notification {

    public enum Type {
        ERROR,
        INFO,
        WARNING
    }

    private Type type;
    private String message;
    private Throwable exception;

    public Notification(Type type, String message, Throwable exception) {
        this.type = type;
        this.message = message;
        this.exception = exception;
    }

    public Type getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    public Throwable getException() {
        return exception;
    }

    public static Notification createError( String message, Throwable error ) {
        return new Notification(Type.ERROR, message, error);
    }

    public static Notification createInfo( String message ) {
        return new Notification(Type.ERROR, message, null);
    }

    public static Notification createAlert( String message ) {
        return new Notification(Type.WARNING, message, null);
    }
}
