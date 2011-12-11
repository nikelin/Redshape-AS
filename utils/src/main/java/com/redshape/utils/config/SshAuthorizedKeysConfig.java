package com.redshape.utils.config;

import com.redshape.utils.StringUtils;
import com.redshape.utils.config.sources.IConfigSource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        try {
            BufferedReader reader = new BufferedReader( this.source.getReader() );
            List<String> parts = new ArrayList<String>();
            String buff;
            while ( null != ( buff = reader.readLine() ) ) {
                parts.add( buff );
            }
            
            for ( String part : parts ) {
                if ( part.trim().startsWith("#")
                        || StringUtils.trim(part).isEmpty() ) {
                    continue;
                }

                this.append( this._processData(part) );
            }
        } catch ( IOException e ) {
            throw new ConfigException( e.getMessage(), e );
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

    @Override
    public String serialize() throws ConfigException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public IConfig createChild(String name) throws ConfigException {
        return new SshAuthorizedKeysConfig(this, name, null);
    }
}
