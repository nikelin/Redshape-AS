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
