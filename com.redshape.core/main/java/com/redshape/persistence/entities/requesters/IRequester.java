package com.redshape.persistence.entities.requesters;

import com.redshape.auth.IIdentity;
import com.redshape.persistence.entities.IEntity;
import com.redshape.persistence.entities.IAddress;
import com.redshape.persistence.entities.IPAddress;

import java.util.Set;

/**
 * WebCam Project
 *
 * @author nikelin
 * @project vio
 * @package com.vio.persistence.entities
 * @date Apr 17, 2010
 */
public interface IRequester<T, V extends IAddress> extends IIdentity<T>, IEntity {

    public void setApiKey( String key );

    public String getApiKey();

    public void setAddress( V address );

    public V getAddress();

    public Set<? extends IRequesterProperty> getProperties();

    public <T extends IRequesterPropertyId> void setProperty( T id, String value );

    public <T extends IRequesterPropertyId> IRequesterProperty getProperty( T id );

}
