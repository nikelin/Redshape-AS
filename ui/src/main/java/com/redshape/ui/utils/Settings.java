package com.redshape.ui.utils;

import com.redshape.utils.config.ConfigException;
import com.redshape.utils.config.IConfig;
import com.redshape.utils.config.IWritableConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * @author nikelin
 * @date 14/04/11
 * @package com.redshape.ui.utils
 */
public class Settings implements IWritableConfig {
	private static final long serialVersionUID = -6125512203291653333L;
	
	private String name;
	private String value;
	private boolean nulled;
	private Settings parent;
	private Map<String, IConfig> children = new HashMap<String, IConfig>();
	private Map<String, String> attributes = new HashMap<String, String>();

	public Settings() {
		this(null);
	}

	public Settings( String name ) {
		this.name = name;
	}

	public Settings( Settings config, String name ) {
		this.parent = config;
		this.name = name;
	}

	public Settings( Settings config, boolean nulled ) {
		this.parent = config;
		this.nulled = nulled;
	}

	protected void setParent( IConfig parent ) {
		if ( !( parent instanceof Settings ) ) {
			throw new IllegalArgumentException("Illegal parent node type");
		}

		this.parent = (Settings) parent;
	}

	protected void remove( IConfig config ) {
		this.children.remove( config );
	}

	protected IConfig createNulledNode() {
		return new Settings( this, true );
	}

	@Override
	public boolean isWritable() {
		return true;
	}

	@Override
	public IWritableConfig append(IConfig config) {
		if ( !( config instanceof Settings ) ) {
			throw new IllegalArgumentException("Illegal child type");
		}

		( (Settings) config ).setParent(config);

		this.children.put( config.name(), config );

		return this;
	}

	@Override
	public IWritableConfig makeWritable(boolean value) {
		return this;
	}

	@Override
	public IWritableConfig set(String value) throws ConfigException {
		this.value = value;
		return this;
	}

	@Override
	public IWritableConfig attribute(String name, String value) {
		this.attributes.put( name, value );
		return this;
	}

	@Override
	public IWritableConfig createChild(String name) {
		if ( this.children.get(name) != null ) {
			return (IWritableConfig) this.children.get(name);
		}

		Settings settings = new Settings(name);
		this.append(settings);
		return settings;
	}

	@Override
	public IWritableConfig remove() throws ConfigException {
		( (Settings) this.parent() ).remove( this );
		return (Settings) this.parent();
	}

	@Override
	public boolean isNull() {
		return this.nulled;
	}

	@Override
	public IConfig get(String name) throws ConfigException {
		if ( this.children.containsKey(name) ) {
			return this.children.get(name);
		}

		return this.createNulledNode();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends IConfig> T[] childs() {
		return  (T[]) this.children.values().toArray( new IConfig[this.children.size()] );
	}

	@Override
	public boolean hasChilds() {
		return this.children.size() > 0;
	}

	@Override
	public String[] list() {
		return this.list(null);
	}

	@Override
	public String[] list(String name) {
		String[] values = new String[this.children.size()];

		int i = 0;
		for ( IConfig child : this.childs() ) {
			if ( name != null && !child.name().equals(name) ) {
				continue;
			}

			values[i++] = child.value();
		}

		return values;
	}

	@Override
	public String name() {
		return this.name;
	}

	@Override
	public String[] names() {
		String[] names = new String[this.children.size()];

		int i = 0;
		for ( IConfig config : this.childs() ) {
			names[i++] = config.name();
		}

		return names;
	}

	@Override
	public String attribute(String name) {
		return this.attributes.get(name);
	}

	@Override
	public String value() {
		return this.value;
	}

	@Override
	public IConfig parent() throws ConfigException {
		return this.parent;
	}

	@Override
	public String serialize() throws ConfigException {
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <V> V getRawElement() {
		return (V) this;
	}
}
