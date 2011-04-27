package com.redshape.ui.bindings.render.properties;

import com.redshape.bindings.BindingException;
import com.redshape.bindings.types.IBindable;
import com.redshape.ui.UIException;
import com.redshape.ui.bindings.properties.IPropertyUI;
import com.redshape.ui.bindings.render.IViewRenderer;
import com.redshape.ui.data.IModelData;
import com.redshape.ui.data.IStore;
import com.redshape.ui.data.loaders.IDataLoader;
import com.redshape.ui.data.loaders.LoaderException;
import com.redshape.ui.data.providers.IProvidersFactory;
import com.redshape.ui.utils.UIRegistry;
import org.apache.log4j.Logger;

import javax.swing.*;

public abstract class AbstractListUI<T> extends JPanel implements IPropertyUI<T> {
	private static final long serialVersionUID = -2887352384241715674L;
	private static final Logger log = Logger.getLogger(AbstractListUI.class);
	
	private IViewRenderer<?> renderingContext;
    private IBindable descriptor;
	
	public AbstractListUI( IViewRenderer<?> renderingContext, IBindable descriptor) {
        this.descriptor = descriptor;
		this.renderingContext = renderingContext;
	}

    protected IBindable getDescriptor() {
        return this.descriptor;
    }

	protected IViewRenderer<?> getRenderingContext() {
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
		} catch ( InstantiationException e ) {
			throw new UIException("Provider exception", e );
		} catch ( LoaderException e ) {
			throw new UIException("Data loader exception", e );
		} catch ( BindingException e ) {
			throw new UIException("Binding exception", e);
		}
	}
}
