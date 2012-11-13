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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.utils.config
 * @date 10/20/11 1:05 PM
 */
public class PropertiesConfig extends AbstractConfig {
	public static final String[] STANDARD_LINE_DELIMITERS = new String[] { "\n", "\r", ";" };

    protected boolean nulled;
    protected String value;
    protected IConfig parent;
    protected String name;
    protected Map<String, String> attributes = new LinkedHashMap<String, String>();
    protected List<IConfig> childs = new ArrayList<IConfig>();
    protected IConfigSource source;

    public PropertiesConfig() {
        this(null, null, null);
    }

    protected PropertiesConfig(PropertiesConfig parent, String name, String value) {
        super(parent, name, value);
    }

    protected PropertiesConfig(String name, String value) {
        this(null, name, value);
    }

    public PropertiesConfig(IConfigSource source) throws ConfigException {
		super(source);
    }

    @Override
    protected void actualInit() throws ConfigException {
        String data = this.source.read();
        if ( data.isEmpty() ) {
            return;
        }

        this.processData( data );
    }

	@Override
	protected IConfig createNull() {
		PropertiesConfig config = new PropertiesConfig(this, null, null);
		config.nulled = true;
		return config;
	}

	protected String[] getLineDelimiters() {
		return STANDARD_LINE_DELIMITERS;
	}

	protected void processData( String data ) throws ConfigException {
		String[] lines = data.split( SimpleStringUtils.join(this.getLineDelimiters(), "") );
		for ( String line : lines ) {
			String[] parts = line.split("=");
			if ( parts.length != 2 ) {
				throw new ConfigException("Config file is corrupted");
			}

			this.append( new PropertiesConfig(this, parts[0], parts[1] ) );
		}
	}

	@Override
	public String serialize() throws ConfigException {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public IConfig createChild(String name) {
		return new PropertiesConfig(this, name, null);
	}

    public static PropertiesConfig fromSource( IConfig config ) throws ConfigException {
        PropertiesConfig result = new PropertiesConfig( config.name(), config.value() );
        for ( IConfig child : config.childs() ) {
            result.append( fromSource(child) );
        }

        for ( String name : config.attributeNames() ) {
            result.attribute(name, config.attribute(name) );
        }

        return result;
    }
}
