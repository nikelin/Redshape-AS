package com.redshape.servlet.core.context;

import com.redshape.servlet.core.IHttpRequest;
import com.redshape.servlet.views.IView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * @author nikelin
 * @date 13:53
 */
public class ContextSwitcher implements IContextSwitcher {
    private Collection<IResponseContext> registry = new HashSet<IResponseContext>();

    public Collection<IResponseContext> getRegistry() {
        return registry;
    }

    public void setRegistry(Collection<IResponseContext> registry) {
        this.registry = registry;
    }

    @Override
    public IResponseContext chooseContext(IHttpRequest request, IView view) {
        IResponseContext result = null;
        List<IResponseContext> variants = new ArrayList<IResponseContext>();
        for ( IResponseContext context : this.registry ) {
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

        if ( result == null ) {
            return variants.get(0);
        }

        return result;
    }
}
