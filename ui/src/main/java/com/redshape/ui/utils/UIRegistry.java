package com.redshape.ui.utils;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 03.01.11
 * Time: 21:25
 * To change this template use File | Settings | File Templates.
 */
public final class UIRegistry {
    private static Map<UIConstants.Attribute, Object> values = new HashMap<UIConstants.Attribute, Object>();
    private static JFrame context;
    private static JMenuBar menu;

    @SuppressWarnings("unchecked")
	public static <V> V set( UIConstants.Attribute id, Object value ) {
        values.put(id, value);
        return (V) get(id);
    }

    @SuppressWarnings("unchecked")
	public static <V> V get( UIConstants.Attribute id ) {
        return (V) values.get(id);
    }

    public static JMenuBar getMenu() {
    	return UIRegistry.menu;
    }
    
    public static void setMenu( JMenuBar menu ) {
    	UIRegistry.menu = menu;
    }
    
	public static void setRootContext( JFrame context ) {
        UIRegistry.context = context;
    }

    @SuppressWarnings("unchecked")
	public static <V extends JFrame> V getRootContext() {
        return (V) context;
    }

}
