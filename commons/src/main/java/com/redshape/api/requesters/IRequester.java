package com.redshape.api.requesters;

import java.util.Set;

import com.redshape.auth.IIdentity;
import com.redshape.io.net.IAddress;

/**
 * WebCam Project
 *
 * @author nikelin
 * @project vio
 * @package com.vio.persistence.entities
 * @date Apr 17, 2010
 */
public interface IRequester extends IIdentity {

    public void setApiKey( String key );

    public String getApiKey();

    public void setAddress( IAddress address );

    public <V extends IAddress> V getAddress();

    public Set<? extends IRequesterProperty> getProperties();

    public void setProperty( IRequesterPropertyId id, String value );

    public <T extends IRequesterPropertyId> IRequesterProperty getProperty( T id );

}
