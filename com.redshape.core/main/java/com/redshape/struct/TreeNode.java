package com.redshape.struct;

import java.util.Collection;

public interface TreeNode<T> {
	
	public T getParent() throws TreeNodeException;
	
	public Collection<T> getChildren() throws TreeNodeException;
	
	public void setParent( T parent );
	
	public boolean isLeaf();
	
	public Integer getLeft();

    public void setLeft( Integer id );
	
	public Integer getRight();

    public void setRight( Integer id );

}
