package com.redshape.ui.data.tree.traverse.impl;

import com.redshape.ui.data.tree.traverse.ITreeWalkingCollector;
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

	@SuppressWarnings("unchecked")
	@Override
	public <T> Collection<T> collect( DefaultMutableTreeNode node, IFilter<DefaultMutableTreeNode> filter ) {
		Collection<T> result = new HashSet<T>();
		for ( Enumeration<DefaultMutableTreeNode> iterator = node.children();
			  iterator.hasMoreElements(); ) {
			DefaultMutableTreeNode childNode = iterator.nextElement();

			if ( filter.filter( childNode ) ) {
				result.add( (T) childNode.getUserObject() );
			}

			if ( childNode.getChildCount() != 0 ) {
				result.addAll( this.<T>collect( childNode, filter ) );
			}
		}

		return result;
	}

}
