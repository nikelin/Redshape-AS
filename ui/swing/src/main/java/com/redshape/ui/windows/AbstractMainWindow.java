package com.redshape.ui.windows;

import com.redshape.ui.utils.UIConstants;
import com.redshape.ui.utils.UIRegistry;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

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
        this(null);
    }
    
    public AbstractMainWindow( String title ) {
        this(title, null);
    }
    
	public AbstractMainWindow( String title, Dimension dimension ) {
        super();

        this.addComponentListener( new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                super.componentShown(e);    //To change body of overridden methods use File | Settings | File Templates.
            }

            @Override
            public void componentResized(ComponentEvent e) {
                AbstractMainWindow.this.doLayout();
            }
        });

        if ( title != null ) {
            this.setTitle(title);
        }
        
        if ( dimension != null ) {
            this.setSize(dimension);
        }
        
        this.configUI();
        this.buildUI();
    }

    abstract protected void configUI();

    protected void buildUI() {
        this.setLayout( new BorderLayout() );

        MenuBar menu;
        this.setMenuBar( menu = this.createMenu() );
        UIRegistry.set( UIConstants.System.MENUBAR, menu );
        
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
    
    abstract protected MenuBar createMenu();

    abstract protected JPanel createBottom();

    private JComponent buildNorthPanel() {
    	Box box = Box.createVerticalBox();

        box.add( (Component) UIRegistry.set( UIConstants.Area.NORTH, this.createNorthPanel() ) );
    	
    	return box;
    }

}
