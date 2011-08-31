package com.redshape.servlet.views;

import com.redshape.servlet.WebApplication;
import com.redshape.servlet.core.controllers.Action;
import com.redshape.servlet.core.controllers.IAction;
import com.redshape.utils.config.ConfigException;
import com.redshape.utils.config.IConfig;

import java.util.regex.Pattern;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.servlet.views
 * @date 8/21/11 12:46 PM
 */
public final class ViewHelper {
	private static final Pattern protocolMatcher = Pattern.compile("(.+?)://(.*?)");

	public static String url( String url ) {
		try {
			String servletPath = getConfig().get("web").get("servletPath").value();
			if ( !url.startsWith( servletPath ) ) {
				return servletPath + normalizeUrl(url);
			}

			return normalizeUrl(url);
		} catch ( ConfigException e ) {
			return url;
		}
	}

	private static String normalizeUrl( String url ) {
		if ( url.startsWith("/") ) {
			return url;
		}

		if ( !ViewHelper.protocolMatcher.matcher(url).find() ) {
			return "/" + url;
		}

		return url;
	}

	public static String action( Class<? extends IAction> action ) {
		Action actionMeta = action.getAnnotation( Action.class );
		if ( actionMeta == null ) {
			return null;
		}

		try {
			return getConfig().get("web").get("servletPath")
					+ "/" + actionMeta.controller() + "/" + actionMeta.name();
		} catch ( ConfigException e ) {
			return "/" + actionMeta.controller() + "/" + actionMeta.name();
		}
	}

	private static IConfig getConfig() {
		return WebApplication.getContext().getBean(IConfig.class);
	}

}
