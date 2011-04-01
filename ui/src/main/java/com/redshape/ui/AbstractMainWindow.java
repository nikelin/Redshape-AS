package com.redshape.ui;

import com.redshape.ui.utils.UIConstants;
import com.redshape.ui.utils.UIRegistry;

import javax.swing.*;
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
	private static final long serialVersionUID = -3138024523185479599L;

	public AbstractMainWindow() {
        super();

        this.configUI();
        this.buildUI();
    }

    abstract protected void configUI();

    protected void buildUI() {
        this.setLayout( new BorderLayout() );

        this.add( (Component) UIRegistry.set( UIConstants.Area.EAST, this.createEastPanel() ), BorderLayout.EAST );
        this.add( this.buildNorthPanel(), BorderLayout.NORTH  );
        this.add( (Component) UIRegistry.set( UIConstants.Area.CENTER, this.createCenterPanel() ), BorderLayout.CENTER );
        this.add( (Component) UIRegistry.set( UIConstants.Area.WEST, this.createWestPanel() ), BorderLayout.WEST );
        this.add( (Component) UIRegistry.set( UIConstants.Area.SOUTH, this.createBottom() ), BorderLayout.SOUTH );
    }
    
    abstract protected JPanel createEastPanel();
    
    abstract protected JPanel createWestPanel();
    
    abstract protected JPanel createNorthPanel();
    
    abstract protected JPanel createCenterPanel();
    
    abstract protected JMenuBar createMenu();

    abstract protected JPanel createBottom();

    private JComponent buildNorthPanel() {
    	Box box = Box.createVerticalBox();
    	
    	box.add( (Component) UIRegistry.set( UIConstants.Area.MENU, this.createMenu() ) );
    	box.add( (Component) UIRegistry.set( UIConstants.Area.NORTH, this.createNorthPanel() ) );
    	
    	return box;
    }
    
    protected JMenuItem createMenuItem( String text, ActionListener listener ) {
        JMenuItem item = new JMenuItem(text);
        item.addActionListener(listener);
        return item;
    }

}
