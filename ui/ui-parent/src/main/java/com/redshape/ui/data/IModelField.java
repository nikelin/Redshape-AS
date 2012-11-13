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

package com.redshape.ui.data;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 10.01.11
 * Time: 19:28
 * To change this template use File | Settings | File Templates.
 */
public interface IModelField extends Serializable {

	public boolean isTransient();
	
	public IModelField markList( boolean value );
	
	public boolean isList();
	
	public IModelField makeTransient( boolean value );
	
	public IModelField setTitle( String title );
	
	public String getTitle();
	
    public IModelField setType( Class<?> type );

    public Class<?> getType();

    public IModelField setRequired( boolean required );

    public boolean isRequired();

    public String getName();

    public IModelField setFormat( String format );

    public String getFormat();

}
