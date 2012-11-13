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

package com.redshape.form;

import java.util.List;

public interface IForm extends IFormItem {

    public void copy( IForm form );

    public <T> void setValue( String path, T value );

    public <T> List<T> getValues( String name );

    public <T> T getValue( String name );

    public <T extends IForm> List<IFormProcessHandler<T>> getProcessHandlers();

    public void removeProcessHandler( IFormProcessHandler<?> handler );

    public void addProcessHandler( IFormProcessHandler<?> handler );

    public void process( IUserRequest request );

    public void setLegend( String legend );

    public boolean hasValue( String path );

    public String getLegend();

    public void setAction( String action );

    public String getAction();

    public void setMethod( String method );

    public String getMethod();

    public <T extends IForm> T findContext( String name );

    public <T, V extends IFormField<T>> V findField( String name );

    public void addField( IFormField<?> field );

    public void removeField( String path );

    public void removeContext( String path );

    public void remove();

    public void removeField( IFormField<?> field );

    public List<IFormField<?>> getFields();

    public void addSubForm( IForm form, String name );

    public void removeSubForm( String name );

    public List<IForm> getSubForms();

    public List<IFormItem> getItems();

}
