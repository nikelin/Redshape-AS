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
public interface IRequester<T, V extends IAddress> extends IIdentity<T> {

    public void setApiKey( String key );

    public String getApiKey();

    public void setAddress( V address );

    public V getAddress();

    public Set<? extends IRequesterProperty> getProperties();

    public <T extends IRequesterPropertyId> void setProperty( T id, String value );

    public <T extends IRequesterPropertyId> IRequesterProperty getProperty( T id );

}
