/*
 * Copyright 2012 Cyril A. Karpenko
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.redshape.utils.config;

import com.redshape.utils.SimpleStringUtils;
import com.redshape.utils.config.sources.IConfigSource;

/**
 * Created by IntelliJ IDEA.
 * User: cyril
 * Date: 12/11/11
 * Time: 1:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class SshAuthorizedKeysConfig extends AbstractTSConfig {

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
    protected void actualInit() throws ConfigException {
        this.lock.lock();

        String[] parts = this.source.read().split("\\n");

        for ( String part : parts ) {
            if ( part.trim().startsWith("#")
                    || SimpleStringUtils.trim(part).isEmpty() ) {
                continue;
            }

            this.append( this._processData(part) );
        }
    }
    
    protected IConfig _processData( String data ) throws ConfigException {
        String[] parts = data.split(",");

        IConfig result = this.createChild("command");
        result.set( SimpleStringUtils.trim(parts[0].split("=")[1], "\"") );

        for ( int i = 1; i < parts.length; i++ ) {
            String part = SimpleStringUtils.trim(parts[i]);

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
