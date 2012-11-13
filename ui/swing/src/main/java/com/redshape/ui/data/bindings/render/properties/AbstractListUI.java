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

package com.redshape.ui.data.bindings.render.properties;

import com.redshape.utils.beans.bindings.BindingException;
import com.redshape.utils.beans.bindings.types.IBindable;
import com.redshape.ui.application.UIException;
import com.redshape.ui.data.IModelData;
import com.redshape.ui.data.IStore;
import com.redshape.ui.data.bindings.properties.IPropertyUI;
import com.redshape.ui.data.bindings.render.IViewRenderer;
import com.redshape.ui.data.loaders.IDataLoader;
import com.redshape.ui.data.loaders.LoaderException;
import com.redshape.ui.data.providers.IProvidersFactory;
import com.redshape.ui.utils.UIRegistry;
import com.redshape.utils.Commons;
import org.apache.log4j.Logger;

import javax.swing.*;

public abstract class AbstractListUI<T> extends JPanel implements IPropertyUI<T, JPanel> {
	private static final long serialVersionUID = -2887352384241715674L;
	private static final Logger log = Logger.getLogger(AbstractListUI.class);
	
	private IViewRenderer<?, ?> renderingContext;
    private IBindable descriptor;
	
	public AbstractListUI( IViewRenderer<?, ?> renderingContext, IBindable descriptor) {
        super();
        
        Commons.checkNotNull(renderingContext);
        Commons.checkNotNull(descriptor);
        
        this.descriptor = descriptor;
		this.renderingContext = renderingContext;
	}

    @Override
    public JPanel asComponent() {
        return this;
    }

    protected IBindable getDescriptor() {
        return this.descriptor;
    }

	protected IViewRenderer<?, ?> getRenderingContext() {
		return this.renderingContext;
	}
	
	protected IProvidersFactory getProvidersFactory() {
		return UIRegistry.getProvidersFactory();
	}
	
	@SuppressWarnings("unchecked")
	protected IStore<?> getProvider() throws UIException {
		try {
			IStore<IModelData> store = this.getProvidersFactory().provide( this.descriptor.asCollectionObject().getElementType() );
			if ( store == null ) {
				log.info("Related store not found");
				return null;
			}
			
			IDataLoader<IModelData> loader = this.getProvidersFactory()
												 .<IModelData>getLoader(
														 this.getRenderingContext(), 
														 (Class<? extends IStore<IModelData>>) 
														 store.getClass() );
			if ( loader != null ) {
				store.setLoader( loader );
				store.load();
			}
			
			return store;
		} catch ( LoaderException e ) {
			throw new UIException("Data loader exception", e );
		} catch ( BindingException e ) {
			throw new UIException("Binding exception", e);
		}
	}
}
