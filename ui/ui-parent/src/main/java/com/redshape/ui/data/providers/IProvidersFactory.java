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

package com.redshape.ui.data.providers;

import com.redshape.ui.data.IModelData;
import com.redshape.ui.data.IStore;
import com.redshape.ui.data.loaders.IDataLoader;

public interface IProvidersFactory {
	
	public <T extends IModelData> IStore<T> provide( Class<?> type );
	
	public <T extends IModelData> IDataLoader<T> getLoader( Object context,
                                                            Class<? extends IStore<T>> store );
	
	public <T extends IModelData> void registerLoader( Object context,
                                                       Class<? extends IStore<T>> store,
                                                       IDataLoader<T> loader );
	
	public void registerProvider( Class<?> type, Class<? extends IStore<?>> store );
	
}
