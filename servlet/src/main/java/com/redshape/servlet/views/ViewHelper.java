package com.redshape.servlet.views;

import com.redshape.servlet.WebApplication;
import com.redshape.servlet.core.controllers.Action;
import com.redshape.servlet.core.controllers.IAction;
import com.redshape.utils.config.ConfigException;
import com.redshape.utils.config.IConfig;

import java.util.HashMap;
import java.util.Map;
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
			if ( url == null ) {
				return null;
			}

			if ( protocolMatcher.matcher(url).find() ) {
				return url;
			}

			String servletPath = getConfig().get("web").get("servletPath").value();
			if ( !url.startsWith( servletPath ) ) {
				return servletPath + normalizeUrl(url);
			}

			return normalizeUrl(url);
		} catch ( ConfigException e ) {
			return url;
		}
	}

	public static String actionName( IAction action ) {
		Action actionMeta = action.getClass().getAnnotation(Action.class);
		if ( actionMeta == null ) {
			return null;
		}

		return actionMeta.name();
	}

	private static String normalizeUrl( String url ) {
		if ( url.startsWith("/") ) {
			return url;
		}

		return "/" + url;
	}

	public static String action( Class<? extends IAction> action ) {
		return action( action, new HashMap<String, Object>() );
	}

	public static String action( Class<? extends IAction> action, Map<String, Object> params ) {
		Action actionMeta = action.getAnnotation( Action.class );
		if ( actionMeta == null ) {
			return null;
		}

		StringBuilder url = new StringBuilder();
		try {
			url.append(getConfig().get("web").get("servletPath"));
		} catch ( ConfigException e ) {
			throw new IllegalStateException("Config related exception", e );
		}

		url.append("/")
			.append( actionMeta.controller() )
			.append( "/" )
			.append( actionMeta.name() );

		if ( !params.isEmpty() ) {
			url.append("?");
		}

		int i = 0;
		for ( String key : params.keySet() ) {
			url.append( key ).append("=").append( params.get(key) );

			if ( i++ != params.size() - 1 ) {
				url.append("&");
			}
		}

		return url.toString();
	}

	private static IConfig getConfig() {
		return WebApplication.getContext().getBean(IConfig.class);
	}

}
