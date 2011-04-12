package com.redshape.ui.tree;

import com.redshape.ui.data.IModelData;
import com.redshape.ui.data.IStore;
import com.redshape.ui.data.stores.StoreEvents;
import com.redshape.ui.events.AppEvent;
import com.redshape.ui.events.IEventHandler;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Enumeration;

/**
 * Abstract tree which provides most commons operations over the nodes.
 *
 * @author nikelin
 * @date 12/04/11
 * @package com.api.deployer.ui.components
 */
public abstract class AbstractTree extends JTree {

	public AbstractTree() {
		super( new DefaultMutableTreeNode(), true );

		this.init();
	}

	protected void init() {
		this.addMouseListener(
			new AbstractTree.ContextHandler(this)
		);

        this.setRootVisible(false);
		this.setShowsRootHandles(true);
	}

	protected DefaultMutableTreeNode getRoot() {
		return (DefaultMutableTreeNode) this.getModel().getRoot();
	}

	abstract protected JPopupMenu createContextMenu( DefaultMutableTreeNode path );

	@SuppressWarnings("unchecked")
	protected Enumeration<DefaultMutableTreeNode> getNodes() {
		return this.getRoot().children();
	}

    protected void showTreeContextMenu( int x, int y ) {
    	this.showTreeContextMenu(x, y, null);
    }

    protected void showTreeContextMenu( int x, int y, DefaultMutableTreeNode path ) {
        this.createContextMenu(path).show(this, x, y);
    }

	protected DefaultMutableTreeNode createNode( Object object ) {
		return new DefaultMutableTreeNode(object);
	}

	protected void insertNode( DefaultMutableTreeNode node ) {
		this.insertNode( this.getRoot(), node );
	}

	protected DefaultMutableTreeNode findNode( Object value ) {
		return this.findNode( this.getRoot(), value );
	}

	protected DefaultMutableTreeNode findNode( DefaultMutableTreeNode context, Object value ) {
		Enumeration<DefaultMutableTreeNode> children = context.children();
		while ( children.hasMoreElements() ) {
			DefaultMutableTreeNode node = children.nextElement();
			if ( node.getUserObject().equals( value ) ) {
				return node;
			}
		}

		return null;
	}

	protected void insertNode( DefaultMutableTreeNode parent, DefaultMutableTreeNode node ) {
		if ( node == null ) {
			return;
		}

		( (DefaultTreeModel) this.getModel() )
			.insertNodeInto(node, parent, parent.getChildCount() );
	}

	public void removeNode( DefaultMutableTreeNode node ) {
		TreePath path = this.getSelectionModel().getSelectionPath();
		if ( path.getPathCount() == 0 ) {
			return;
		}

		this.getRoot().remove(
				((DefaultMutableTreeNode) path.getLastPathComponent() ) );

	}

	public final static class ContextHandler extends MouseAdapter {
		private AbstractTree context;

		public ContextHandler( AbstractTree tree ) {
			this.context = tree;
		}

        @Override
        public void mousePressed(MouseEvent e) {
            int selRow = this.context.getRowForLocation( e.getX(), e.getY() );
            TreePath selPath = this.context.getPathForLocation( e.getX(), e.getY() );

            DefaultMutableTreeNode target = null;
            if ( selPath != null ) {
            	target = (DefaultMutableTreeNode) selPath.getLastPathComponent();
            }

            if (e.getButton() == MouseEvent.BUTTON3 ) {
				if (selRow != -1) {
                    this.context.showTreeContextMenu( e.getX(), e.getY(), target );
				} else {
					this.context.showTreeContextMenu( e.getX(), e.getY() );
				}
			}
        }
	}

}
