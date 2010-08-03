package com.vio.persistence.entities.tree;

import com.vio.persistence.entities.AbstractEntity;
import com.vio.persistence.entities.tree.fetchers.FetcherException;
import com.vio.persistence.entities.tree.fetchers.FetcherInterface;
import com.vio.persistence.entities.tree.fetchers.NestedSet;
import com.vio.persistence.managers.ManagerException;
import com.vio.struct.TreeNodeException;
import org.apache.log4j.Logger;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author nikelin
 * @about Переделать по возможности через jassist или AspectJ
 * 
 * @TODO getNextSibling(), getPrevSibling()
 * @TODO Реализовать возможность работы непосредственно с объектами запроса либо предоставить интерфейс 
 * доступа к lazyLoad-результатам
 * 
 */
@MappedSuperclass
@Table( uniqueConstraints = {
    @UniqueConstraint( columnNames = { "right", "left" } )
})
abstract public class NestedSetNode<T extends TreeNodeEntity> extends AbstractEntity<T> implements TreeNodeEntity<T> {
    private static final Logger log = Logger.getLogger( NestedSetNode.class );
    
    @Transient
	private Class<T> entityClass;

    @Basic
    @Column( nullable = true, name = "right_idx" )
	private Integer right;

    @Basic
    @Column( nullable = true, name = "left_idx" )
	private Integer left;

    @Transient
    private T parent;

    @Transient
	private List<T> parents = new ArrayList<T>();

    @Transient
	private List<T> children = new ArrayList<T>();

    @Transient
	private FetcherInterface<T> fetcher;

	public NestedSetNode( Class<T> entityClass ) {
		this.entityClass = entityClass;
        this.fetcher = new NestedSet<T>(this, entityClass);
	}
	
	public NestedSetNode( Class<T> entityClass, FetcherInterface<T> fetcher ) {
		this.entityClass = entityClass;
		this.fetcher = fetcher;
	}

    public void setChildren( List<T> children ) {
        this.children = children;
    }

	public List<T> getChildren() throws TreeNodeException {
		return this.getChildren(false);
	}
	
	public List<T> getChildren( boolean forceReload ) throws TreeNodeException {
        try {
            if ( this.children == null || forceReload ) {
                this.children = this.getFetcher().fetchChildren();
            }
            
            return this.children;
        } catch ( FetcherException e ) {
            throw new TreeNodeException();
        }
	}
	
	public T getParent() throws TreeNodeException {
        return this.getParent( false );
	}
	
	public T getParent( boolean forceReload) throws TreeNodeException {
        try {
            if ( this.parent == null || forceReload ) {
                this.parent = this.getFetcher().fetchParent();
            }

            return this.parent;
        } catch ( FetcherException e ) {
            log.error( e.getMessage(), e );
            throw new TreeNodeException();
        }
	}

	protected FetcherInterface<T> getFetcher() {
		return this.fetcher;
	}
	
	public boolean isLeaf() {
		return ( this.getRight() - this.getLeft() ) == 1;
	}

    public void setParent( int parentId ) throws TreeNodeException {
        try {
            this.setParent( (T) this.getDAO().find( parentId ) );
        } catch (ManagerException e ) {
            throw new TreeNodeException();
        }
    }

	public void setParent( T parent ) {
		this.parent = parent;

        this.setIds( parent );
	}

    public void setLeft( Integer id ) {
        this.left = id;
    }

	public Integer getLeft() {
		return this.left;
	}

    public void setRight( Integer id ) {
        this.right = id;
    }

	public Integer getRight() {
		return this.right;
	}

    public T getLastNode() throws TreeNodeException {
        try {
            return this.getFetcher().fetchLastNode();
        } catch ( FetcherException e ) {
            throw new TreeNodeException();
        }
    }

    @PrePersist
    public void setIds() throws TreeNodeException {
        try {
            if ( this.getRight() != null && this.getLeft() != null ) {
                return;
            }

            if ( this.getParent() != null ) {
                T parent = this.getParent();

                this.setIds( parent );

                parent.setRight( parent.getRight() + 1 );
                parent.save();
            } else {
                if ( this.getLastNode() != null ) {
                    this.setIds( this.getLastNode() );
                } else {
                    this.setLeft(0);
                    this.setRight(1);
                }
            }
        } catch ( Throwable e ) {
            log.error( e.getMessage(), e );
            throw new TreeNodeException();
        }
    }

    /**
     * @FIX
     * @param parent
     * @param <T>
     */
    private <T extends TreeNodeEntity> void setIds( T parent ) {
        this.setLeft( parent.getRight() );
        this.setRight( parent.getRight() + 1 );
    }
}   