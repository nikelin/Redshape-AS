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

package com.redshape.i18n.impl;

import com.redshape.i18n.I18NManager;
import com.redshape.utils.ResourcesLoader;
import com.redshape.utils.config.ConfigException;
import com.redshape.utils.config.IConfig;
import com.redshape.utils.config.XMLConfig;
import com.redshape.utils.helpers.XMLHelper;
import org.apache.commons.collections.FastArrayList;
import org.apache.commons.collections.FastHashMap;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Locale;
import java.util.Map;

public class StandardI18N implements I18NManager {
	private static final Logger log = Logger.getLogger( StandardI18N.class );
	
	private String cleanPattern = "[\\n\\r\\s\\\"\\\'\\.,:!\\?@#\\$%&]";
	
	@SuppressWarnings("unchecked")
	private List<String> locales = new FastArrayList();
	@SuppressWarnings("unchecked")
	private Map<String, Integer> searchIndex = new FastHashMap();
	private String resource;
	private Locale locale;
	private ResourcesLoader loader;
	
	public StandardI18N( ResourcesLoader loader, String resource, Locale locale )
		throws ConfigException {
		if ( loader == null ) {
			throw new IllegalArgumentException("<null>");
		}
		
		if ( locale == null ) {
			throw new IllegalArgumentException("<null>");
		}
		
		this.loader = loader;
		this.resource = resource;
		this.locale = locale;
		
		this.load();
	}
	
	public void setResourcesLoader( ResourcesLoader loader ) {
		this.loader = loader;
	}
	
	protected ResourcesLoader getResourcesLoader() {
		return this.loader;
	}
	
	@Override
	public String _(String id) {
		log.info( "Locale:" + this.locale.toString() );
		
		Integer translateId = this.searchIndex.get( this.normalize( id ) );
		if ( translateId == null ) {
			return id;
		}
		
		return this.locales.get(translateId);
	}
	
	@SuppressWarnings("unchecked")
	protected void load() throws ConfigException {
		XMLConfig config = new XMLConfig( new XMLHelper(this.getResourcesLoader()), this.resource );

		this.searchIndex = new FastHashMap( config.childs().size() );
		for ( IConfig messageNode : config.childs() ) {
			this.locales.add( messageNode.get("target").value() );
			this.searchIndex.put( this.normalize( messageNode.get("orig").value() ),
										this.locales.size() - 1 );
		}
	}
	
	protected String normalize( String value ) {
		if ( value == null ) {
			throw new IllegalArgumentException("<null>");
		}
		
		return value.toLowerCase().replaceAll( this.cleanPattern, "");
	}
	
}
