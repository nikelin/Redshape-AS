/*
 * Copyright 2012 Cyril A. Karpenko
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.redshape.ui.data.tree;

import com.redshape.ui.data.tree.traverse.ITreeWalker;
import com.redshape.ui.data.tree.traverse.ITreeWalkingCollector;
import com.redshape.ui.data.tree.traverse.impl.DefaultWalker;
import com.redshape.ui.data.tree.traverse.impl.DefaultWalkingCollector;
import com.redshape.utils.IFilter;

import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collection;
import java.util.Enumeration;

/**
 * Abstract tree which provides most commons operations over the nodes.
 *
 * @author nikelin
 * @date 12/04/11
 * @package com.redshape.ui.components
 */
public abstract class AbstractTree extends JTree {
	private static final long serialVersionUID = -5271871795768545970L;
	
	private ITreeWalker walker;
	private ITreeWalkingCollector collector;

	public AbstractTree() {
		super( new DefaultMutableTreeNode(), true );

		this.setCollector( new DefaultWalkingCollector() );
		this.setWalker( new DefaultWalker() );

        this.setCellRenderer( new DefaultTreeCellRenderer() {
			private static final long serialVersionUID = 2452269545705322785L;

			@Override
            public Component getTreeCellRendererComponent(
                JTree tree, Object value, boolean sel, boolean expanded,
                boolean leaf, int root, boolean hasFocus ) {
                Component result = super.getTreeCellRendererComponent( tree, value, sel, expanded,
                        leaf, root, hasFocus );

                if ( leaf ) {
                     this.setIcon( UIManager.getIcon("Tree.closeIcon") );
                } else {
                    if ( expanded ) {
                        this.setIcon( UIManager.getIcon("Tree.closeIcon") );
                    } else {
                         this.setIcon( UIManager.getIcon("Tree.closeIcon") );
                    }
                }

                return result;
            }
        });

		this.init();
	}

	protected void setWalker( ITreeWalker walker ) {
		this.walker = walker;
	}

	protected void setCollector( ITreeWalkingCollector collector ) {
		this.collector = collector;
	}

	public void walk( IFilter<?> filter ) {
		this.walk( this.getRoot(), filter );
	}

	public void walk( DefaultMutableTreeNode context, IFilter<?> filter ) {
		this.walker.walk( context, filter );
	}

	public <T> Collection<T> collect( IFilter<DefaultMutableTreeNode> filter ) {
		return this.<T>collect( this.getRoot(), filter );
	}

	public <T> Collection<T> collect( DefaultMutableTreeNode node, IFilter<DefaultMutableTreeNode> filter ) {
		return this.collector.<T>collect( node, filter );
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

	@SuppressWarnings("unchecked")
	public void removeNodes() {
		for ( Enumeration<DefaultMutableTreeNode> iterator = this.getRoot().children(); iterator.hasMoreElements(); ) {
			this.removeNode( iterator.nextElement() );
		}
	}

	protected void insertNode( DefaultMutableTreeNode node ) {
		this.insertNode( this.getRoot(), node );
	}

	protected DefaultMutableTreeNode findNode( Object value ) {
		return this.findNode( this.getRoot(), value );
	}

	@SuppressWarnings("unchecked")
	protected DefaultMutableTreeNode findNode( DefaultMutableTreeNode context, Object value ) {
		Enumeration<DefaultMutableTreeNode> children = context.children();
		while ( children.hasMoreElements() ) {
			DefaultMutableTreeNode node = children.nextElement();
			if ( node.getUserObject().equals( value ) ) {
				return node;
			}

			if ( node.getChildCount() != 0 ) {
				node = this.findNode( node, value );
				if ( node != null ) {
					return node;
				}
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
		this.removeNode( this.getRoot(), node );
	}

	public void removeNode( DefaultMutableTreeNode parent, DefaultMutableTreeNode node ) {
		parent.remove( node );
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
