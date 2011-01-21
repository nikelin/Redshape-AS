package com.redshape.persistence.entities.tree.fetchers;

import com.redshape.persistence.entities.IEntity;
import com.redshape.persistence.entities.tree.NestedSetNode;
import com.redshape.struct.TreeNode;

import org.apache.log4j.Logger;

import java.util.Collection;

public class NestedSet<T extends TreeNode<T> & IEntity> implements FetcherInterface<T> {
    private static final Logger log = Logger.getLogger( NestedSet.class );

	private Class<T> entityClass;
	private NestedSetNode<T> node;
	
	public NestedSet( NestedSetNode<T> node, Class<T> clazz ) {
		this.entityClass = clazz;
		this.node = node;
	}
	
	// @FIXME: due to DAO refactoring
	public Collection<T> fetchChildren() throws FetcherException {
        return null;
	}
	
	// @FIXME: due to DAO refactoring
	public T fetchParent() throws FetcherException {
        return null;
    }

	// @FIXME: due to DAO refactoring
    public T fetchLastNode() throws FetcherException {
        return null;
    }

    // @FIXME: due to DAO refactoring
    public T fetchNode( Integer left, Integer right ) throws FetcherException {
        return null;
    }
	
	private NestedSetNode<T> getNode() {
		return this.node;
	}
}
