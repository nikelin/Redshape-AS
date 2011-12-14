package com.redshape.ui.utils;

import com.redshape.utils.config.AbstractConfig;
import com.redshape.utils.config.ConfigException;
import com.redshape.utils.config.IConfig;
import com.redshape.utils.config.sources.IConfigSource;

import java.io.File;

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
	protected void init() throws ConfigException {
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
