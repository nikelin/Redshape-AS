package com.redshape.i18n;

import java.util.Locale;

import com.redshape.utils.ResourcesLoader;
import com.redshape.utils.config.ConfigException;

public interface I18NFacade {

	/**
	 * Create new I18N manager
	 * @param loader
	 * @param resources
	 * @param locale
	 * @return
	 */
	public I18NManager createManager( ResourcesLoader loader, 
									  String resources, 
									  Locale locale ) throws ConfigException;
	
	/**
	 * Get I18N manager associated with a given locale
	 * @param loader
	 * @param resources
	 * @param locale
	 * @return
	 */
	public I18NManager getManager( Locale locale );
	
	/**
	 * Get normalized locale name
	 * 
	 * @return
	 */
	public String normalizeLocale( Locale locale );
	
}
