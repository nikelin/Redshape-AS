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

package com.redshape.ui.data.loaders;

import com.redshape.ui.application.events.EventDispatcher;
import com.redshape.ui.data.IModelData;

import java.util.Collection;

public abstract class AbstractDataLoader<T extends IModelData>
									extends EventDispatcher
									implements IDataLoader<T> {
	private static final long serialVersionUID = 7205617290953493540L;

	@Override
    public Collection<T> preProcess( Collection<T> results ) {
        return results;
    }

	@Override
	public void load() throws LoaderException {
		try { 
			this.forwardEvent( LoaderEvents.BeforeLoad );

			this.forwardEvent( LoaderEvents.Loaded, this.preProcess( this.doLoad() ) );
		} catch ( LoaderException e ) {
			throw e;
		} catch ( Throwable e ) {
			this.forwardEvent( LoaderEvents.Error );
		}
	}
	
	protected abstract Collection<T> doLoad() throws LoaderException;

}
