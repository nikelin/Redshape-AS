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

package com.redshape.ui.utils;

import com.redshape.utils.config.AbstractConfig;
import com.redshape.utils.config.ConfigException;
import com.redshape.utils.config.IConfig;
import com.redshape.utils.config.sources.IConfigSource;

/**
 * @author nikelin
 * @date 14/04/11
 * @package com.redshape.ui.utils
 */
public class Settings extends AbstractConfig {
    private static final long serialVersionUID = 3111353072614008854L;

	public Settings() {
		this(null, null, null);
	}

	public Settings(IConfig parent, String name, String value) {
		super(parent, name, value);
	}

	public Settings(String name, String value) {
		super(name, value);
	}

	public Settings(IConfigSource source) throws ConfigException {
		super(source);
	}

	@Override
	protected void actualInit() throws ConfigException {
		//To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	protected IConfig createNull() {
		Settings settings = new Settings(null, null, null);
		settings.nulled = true;
		return settings;
	}

	@Override
	public String serialize() throws ConfigException {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public IConfig createChild(String name) throws ConfigException {
		return new Settings(this, name, null);
	}
}
