package com.redshape.net;

import com.redshape.utils.IParametrized;

import java.net.URI;

/**
 * @author nikelin
 * @date 12:54
 */
public interface IServer extends IParametrized<ServerAttribute> {

    public ServerType getType();

    public void setType( ServerType type );

    public URI toURI();
    
    public void fromURI( URI uri );

}
