package com.redshape.servlet.core.context;

import com.redshape.servlet.core.IHttpRequest;
import com.redshape.servlet.views.IView;

import java.util.*;

/**
 * @author nikelin
 * @date 13:53
 */
public class ContextSwitcher implements IContextSwitcher {
    private Map<ContextId, IResponseContext> registry = new HashMap<ContextId, IResponseContext>();

    public Map<ContextId, IResponseContext> getRegistry() {
        return registry;
    }

    public void setRegistry(Map<ContextId, IResponseContext> registry) {
        this.registry = registry;
    }

    @Override
    public boolean isCompitable(IHttpRequest request, ContextId contextId) {
        IResponseContext context = this.chooseContext(contextId);
        SupportType supportType = context.isSupported(request);
        switch ( supportType ) {
            case MAY:
            case SHOULD:
                return true;
            case NO:
            default:
                return false;
        }
    }

    @Override
    public IResponseContext chooseContext(ContextId contextId) {
        return this.registry.get(contextId);
    }

    @Override
    public IResponseContext chooseContext(IHttpRequest request, IView view) {
        IResponseContext result = null;
        List<IResponseContext> variants = new ArrayList<IResponseContext>();
        for ( IResponseContext context : this.registry.values() ) {
            SupportType type = context.isSupported( request );
            switch ( type ) {
                case SHOULD:
                    result = context;
                    break;
                case MAY:
					if ( context.isSupported( view ).equals( SupportType.SHOULD ) ) {
						result = context;
					} else {
                    	variants.add( context );
					}
				break;
            }

            if ( result != null ) {
                break;
            }
        }

        if ( result == null && !variants.isEmpty() ) {
            return variants.get(0);
        }

        return result;
    }
}
