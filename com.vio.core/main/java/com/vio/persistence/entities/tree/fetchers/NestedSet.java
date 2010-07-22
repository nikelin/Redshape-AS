package com.vio.persistence.entities.tree.fetchers;

import com.vio.persistence.entities.tree.NestedSetNode;
import com.vio.persistence.entities.tree.TreeNodeEntity;
import com.vio.persistence.managers.ManagerException;
import com.vio.persistence.managers.ManagersFactory;
import org.apache.log4j.Logger;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NestedSet<T extends TreeNodeEntity> implements FetcherInterface<T> {
    private static final Logger log = Logger.getLogger( NestedSet.class );

	private Class<T> entityClass;
	private NestedSetNode<T> node;
	
	public NestedSet( NestedSetNode<T> node, Class<T> clazz ) {
		this.entityClass = clazz;
		this.node = node;
	}
	
	public List<T> fetchChildren() throws FetcherException {
        try {
            return ManagersFactory.getDefault()
                                            .getForEntity( this.entityClass )
                                                .findMatches(
                                                    new String[] { "left", "right"},
                                                    new Object[] { this.getNode().getLeft()  == null ? 0 : this.getNode().getLeft(),
                                                           this.getNode().getRight()  == null ? 0 : this.getNode().getRight()
                                                    },
                                                    new String[] { ">", "<" } );
        } catch ( ManagerException e ) {
            throw new FetcherException();
        }
	}
	
	public T fetchParent() throws FetcherException {
        try {
            return (T) ManagersFactory.getDefault()
                                            .getForEntity( this.entityClass )
                                                .findOneMatched(
                                                    new String[] { "left", "right"},
                                                    new Object[] { this.getNode().getLeft() == null ? 0 : this.getNode().getLeft(),
                                                           this.getNode().getRight() == null ? 0 : this.getNode().getRight()  
                                                    },
                                                    new String[] { "<", ">" } );
        } catch ( ManagerException e ) {
            log.error( e.getMessage(), e );
            throw new FetcherException();
        }
    }

    public T fetchLastNode() throws FetcherException {
        try {
            //@FIXME: refactor using findMatches/findOneMatched
            Query q = this.getNode()
                            .getDAO()
                                .createQuery( "from " + this.getNode().getEntityName() + " as l_entity where " 
                                              + "l_entity.right = ( "
                                                + "select max(e.right) from " + this.getNode().getEntityName() + " as e"
                                              + ")");

            T result = null;
            try {
                result = (T) q.getSingleResult();
            } catch ( NoResultException e ) { }

            return result;
        } catch ( ManagerException e ) {
            throw new FetcherException();
        }
    }

    public T fetchNode( Integer left, Integer right ) throws FetcherException {
        try {
            Map<String, Object> constrain = new HashMap<String, Object>();
            constrain.put("left", left);
            constrain.put("right", right );

            Object result = ManagersFactory.getDefault().getForEntity( this.getNode() ).findMatches( constrain );
            if ( result != null ) {
                result = ( (List) result ).get(0);
            }

            return (T) result;
        } catch ( ManagerException e ) {
            throw new FetcherException();
        }
    }
	
	private NestedSetNode<T> getNode() {
		return this.node;
	}
}
