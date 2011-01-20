package com.redshape.ui;

import com.redshape.ui.Dispatcher;
import com.redshape.ui.events.UIEvents;
import com.redshape.makeitlive.ui.utils.UIConstants;
import com.redshape.makeitlive.ui.utils.UIRegistry;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 03.01.11
 * Time: 21:19
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractMainWindow extends JFrame {

    public AbstractMainWindow() {
        super();

        this.configUI();
        this.buildUI();
    }

    abstract protected void configUI();

    protected void buildUI() {
        this.setLayout( new BorderLayout() );

        this.add( (Component) UIRegistry.set(UIConstants.MAIN_MENU, this.createMenu()), BorderLayout.NORTH );
        this.add( (Component) UIRegistry.set( UIConstants.CENTER_PANE, new JPanel()), BorderLayout.CENTER );
        this.add( (Component) UIRegistry.set( UIConstants.BOTTOM_PANE, this.createBottom() ), BorderLayout.SOUTH );
    }

    abstract protected JMenuBar createMenu();

    abstract protected JPanel createBottom();

    protected JMenuItem createMenuItem( String text, ActionListener listener ) {
        JMenuItem item = new JMenuItem(text);
        item.addActionListener(listener);

        return item;
    }

}
