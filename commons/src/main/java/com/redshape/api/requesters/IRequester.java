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

    public IAddress getAddress();

    public <V extends IRequesterProperty> Set<V> getProperties();

    public <V extends IRequesterPropertyId> void setProperty( V id, String value );

    public <V extends IRequesterPropertyId> IRequesterProperty getProperty( V id );

}
