package com.redshape.plugins.loaders;

import com.redshape.utils.IEnum;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.plugins.loaders
 * @date 10/11/11 12:22 PM
 */
public class PluginType implements IEnum<String> {
	private String name;

	protected PluginType( String type ) {
		this.name = type;
	}

	public static final PluginType JAVA = new PluginType("PluginType.Java");
	public static final PluginType JYTHON = new PluginType("PluginType.Jython");
	public static final PluginType JRUBY = new PluginType("PluginType.JRuby");

	@Override
	public String name() {
		return this.name;
	}

	@Override
	public int hashCode() {
		return this.name.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof PluginType
				&& obj != null
				&& (( PluginType ) obj).name().equals( this.name );
	}
}
