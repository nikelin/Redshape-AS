package com.redshape.ui.utils;

import com.redshape.utils.IEnum;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 03.01.11
 * Time: 21:23
 * To change this template use File | Settings | File Templates.
 */
public final class UIConstants {
	public interface Attribute extends IEnum<String> {
	}
	
	public enum Area implements Attribute {
		NONE,
		SOUTH,
		NORTH,
		CENTER,
		EAST,
		WEST,
		MENU
	}
	
	public enum System implements Attribute {
		SPRING_CONTEXT
	}
}
