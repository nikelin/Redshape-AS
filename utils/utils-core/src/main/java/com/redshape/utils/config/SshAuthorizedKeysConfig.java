package com.redshape.utils.config;

import com.redshape.utils.StringUtils;
import com.redshape.utils.config.sources.IConfigSource;

/**
 * Created by IntelliJ IDEA.
 * User: cyril
 * Date: 12/11/11
 * Time: 1:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class SshAuthorizedKeysConfig extends AbstractConfig {

    public SshAuthorizedKeysConfig() {
        this(null, null);
    }

    public SshAuthorizedKeysConfig(IConfig parent, String name, String value) {
        super(parent, name, value);
    }

    public SshAuthorizedKeysConfig(String name, String value) {
        super(name, value);
    }

    public SshAuthorizedKeysConfig(IConfigSource source) throws ConfigException {
        super(source);
    }

    @Override
    protected void init() throws ConfigException {
        String[] parts = this.source.read().split("\\n");

        for ( String part : parts ) {
            if ( part.trim().startsWith("#")
                    || StringUtils.trim(part).isEmpty() ) {
                continue;
            }

            this.append( this._processData(part) );
        }
    }
    
    protected IConfig _processData( String data ) throws ConfigException {
        String[] parts = data.split(",");

        IConfig result = this.createChild("command");
        result.set( StringUtils.trim( parts[0].split("=")[1], "\"") );

        for ( int i = 1; i < parts.length; i++ ) {
            String part = StringUtils.trim(parts[i]);

            if ( part.contains("ssh") ) {
                String[] sshKeyParts = part.split("\\s");

                result.attribute( sshKeyParts[0], "");
                result.attribute( sshKeyParts[1], sshKeyParts[2] );

                if ( sshKeyParts.length > 2 ) {
                    result.attribute("user", sshKeyParts[3] );
                }
            } else {
                result.attribute( part, "" );
            }
        }
        
        return result;
    }

    @Override
    protected IConfig createNull() {
        return new SshAuthorizedKeysConfig();
    }

    /**
     * @todo: reword
     * @return
     * @throws ConfigException
     */
    @Override
    public String serialize() throws ConfigException {
        StringBuilder builder = new StringBuilder();
        for ( IConfig node : this.childs() ) {
            builder.append("command=\"")
               .append( node.value() )
               .append("\",");

            int i = 0;
            for ( String attribute : node.attributeNames() ) {
                String value = node.attribute(attribute);
                if ( value == null || value.isEmpty() ) {
                    builder.append(attribute);
                } else {
                    builder.append(attribute)
                           .append(" ")
                           .append( value );
                }
                
                if ( i++ < node.attributeNames().length - 2 ) {
                    builder.append(",");
                }
            }

            builder.append("\n");
        }

        return builder.toString();
    }

    @Override
    public IConfig createChild(String name) throws ConfigException {
        return new SshAuthorizedKeysConfig(this, name, null);
    }
}
