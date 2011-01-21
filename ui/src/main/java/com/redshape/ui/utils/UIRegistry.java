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
    private static Map<Integer, Object> values = new HashMap<Integer, Object>();
    private static JFrame context;

    public static <V> V set( Integer id, Object value ) {
        values.put(id, value);
        return (V) get(id);
    }

    public static <V> V get( Integer id ) {
        return (V) values.get(id);
    }

    public void setRootContext( JFrame context ) {
        this.context = context;
    }

    public static <V extends JFrame> V getRootContext() {
        return (V) context;
    }

}
