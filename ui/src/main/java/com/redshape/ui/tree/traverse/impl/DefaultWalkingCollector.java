package com.redshape.ui.tree.traverse.impl;

import com.redshape.ui.tree.traverse.ITreeWalker;
import com.redshape.ui.tree.traverse.ITreeWalkingCollector;
import com.redshape.utils.IFilter;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;

/**
 * Default collector implementation
 *
 * @author nikelin
 * @date 14/04/11
 * @package com.redshape.ui.tree.traverse.impl
 */
public class DefaultWalkingCollector implements ITreeWalkingCollector {

	@Override
	public <T> Collection<T> collect( DefaultMutableTreeNode node, IFilter<T> filter ) {
		Collection<T> result = new HashSet<T>();
		for ( Enumeration<DefaultMutableTreeNode> iterator = node.children();
			  iterator.hasMoreElements(); ) {
			DefaultMutableTreeNode childNode = iterator.nextElement();

			T object = (T) childNode.getUserObject();
			if ( filter.filter( object ) ) {
				result.add( object );
			}

			if ( childNode.getChildCount() != 0 ) {
				this.collect( childNode, filter );
			}
		}

		return result;
	}

}
