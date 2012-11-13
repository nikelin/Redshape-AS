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
