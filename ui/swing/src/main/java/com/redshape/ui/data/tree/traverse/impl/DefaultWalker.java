package com.redshape.ui.data.tree.traverse.impl;

import com.redshape.ui.data.tree.traverse.ITreeWalker;
import com.redshape.utils.IFilter;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.Enumeration;

/**
 * @author nikelin
 * @date 14/04/11
 * @package com.redshape.ui.tree.traverse.impl
 */
public class DefaultWalker implements ITreeWalker {

	@SuppressWarnings("unchecked")
	@Override
	public <T> void walk( DefaultMutableTreeNode node, IFilter<T> filter ) {
		for ( Enumeration<DefaultMutableTreeNode> iterator = node.children();
			  iterator.hasMoreElements(); ) {
			DefaultMutableTreeNode childNode = iterator.nextElement();

			T object = (T) childNode.getUserObject();

			filter.filter( object );

			if ( childNode.getChildCount() != 0 ) {
				this.walk( childNode, filter );
			}
		}
	}

}
