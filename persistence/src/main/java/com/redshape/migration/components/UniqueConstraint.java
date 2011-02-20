package com.redshape.migration.components;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.redshape.renderer.Renderable;

/**
 * WebCam Project
 *
 * @author nikelin
 * @project vio
 * @package com.vio.migration.components
 * @date Apr 6, 2010
 */
@Renderable
public class UniqueConstraint {
    private String name;
    private IndexType type;
    private Set<String> columns = new HashSet<String>();

    public UniqueConstraint() {}

    public UniqueConstraint( String name ) {
        this(name, IndexType.BTREE);
    }

    public UniqueConstraint( String name, IndexType type ) {
        this(name, type, new HashSet() );
    }

    public UniqueConstraint( String name, IndexType type, String[] columns ) {
        this(name, type, new HashSet( Arrays.asList(columns) ) );
    }

    public UniqueConstraint( String name, IndexType type, Set<String> columns ) {
        this.name = name;
        this.type = type;
        this.columns = columns;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setType( IndexType type ) {
        this.type = type;
    }

    public IndexType getType() {
        return this.type;
    }

    public void addColumn( String column ) {
        this.columns.add(column);
    }

    public void setColumns( Set<String> columns ) {
        this.columns = columns;
    }

    public Set<String> getColumns() {
        return this.columns;
    }
}
