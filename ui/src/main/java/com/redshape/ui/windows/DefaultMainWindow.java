package com.redshape.ui.windows;

import javax.swing.*;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: cyril
 * Date: 12/9/11
 * Time: 2:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class DefaultMainWindow extends AbstractMainWindow {

    public DefaultMainWindow() {
        this(null);
    }

    public DefaultMainWindow( String title ) {
        this(title, null);
    }

    public DefaultMainWindow( String title, Dimension size ) {
        super( title, size );
    }

    @Override
    protected void configUI() {}

    @Override
    protected JPanel createEastPanel() {
        return new JPanel();
    }

    @Override
    protected JPanel createWestPanel() {
        return new JPanel();
    }

    @Override
    protected JPanel createNorthPanel() {
        return new JPanel();
    }

    @Override
    protected JPanel createCenterPanel() {
        JPanel panel = new JPanel();
        panel.setMinimumSize( new Dimension(400, 500) );
        return panel;
    }

    @Override
    protected MenuBar createMenu() {
        return new MenuBar();
    }

    @Override
    protected JPanel createBottom() {
        JPanel panel = new JPanel();
        panel.setBorder( BorderFactory.createEtchedBorder() );
        panel.setBackground( Color.GRAY );
        return panel;
    }
}
