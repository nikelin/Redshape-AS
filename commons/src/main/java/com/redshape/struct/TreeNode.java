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

package com.redshape.struct;

import java.util.Collection;

public interface TreeNode<T extends TreeNode<T>> {
	
	public T getParent() throws TreeNodeException;
	
	public Collection<T> getChildren() throws TreeNodeException;
	
	public void setParent( T parent );
	
	public boolean isLeaf();
	
	public Integer getLeft();

    public void setLeft( Integer id );
	
	public Integer getRight();

    public void setRight( Integer id );

}
