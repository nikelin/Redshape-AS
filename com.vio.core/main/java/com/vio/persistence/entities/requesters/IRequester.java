package com.vio.persistence.entities.requesters;

import com.vio.auth.Identity;
import com.vio.persistence.entities.IEntity;
import com.vio.persistence.entities.IAddress;
import com.vio.persistence.entities.IPAddress;

import java.util.Set;

/**
 * WebCam Project
 *
 * @author nikelin
 * @project vio
 * @package com.vio.persistence.entities
 * @date Apr 17, 2010
 */
public interface IRequester<T> extends Identity<T>, IEntity {

    public void setApiKey( String key );

    public String getApiKey();

    public void setAddress( IPAddress address );

    public IAddress getAddress();

    public Set<? extends IRequesterProperty> getProperties();

    public <T extends IRequesterPropertyId> void setProperty( T id, String value );

    public <T extends IRequesterPropertyId> IRequesterProperty getProperty( T id );

}
