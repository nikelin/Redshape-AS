package com.redshape.ui.data.tree.traverse;

import com.redshape.utils.IFilter;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * @author nikelin
 * @date 14/04/11
 * @package com.redshape.ui.tree
 */
public interface ITreeWalker {

	public <T> void walk( DefaultMutableTreeNode node, IFilter<T> filter );

}
