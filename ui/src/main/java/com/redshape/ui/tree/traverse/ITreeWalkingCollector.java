package com.redshape.ui.tree.traverse;

import com.redshape.utils.IFilter;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.Collection;

/**
 * Walker which main goal to traverse tree structure and
 * collect all matching filter nodes.
 *
 * @author nikelin
 * @date 14/04/11
 * @package com.redshape.ui.tree
 */
public interface ITreeWalkingCollector {

	public <T> Collection<T> collect( DefaultMutableTreeNode node, IFilter<DefaultMutableTreeNode> filter );

}
