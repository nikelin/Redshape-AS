package com.vio.migration.components;

import com.vio.render.IRenderable;

import java.util.HashSet;
import java.util.Set;

/**
 * WebCam Project
 *
 * @author nikelin
 * @project vio
 * @package com.vio.migration.components
 * @date Apr 6, 2010
 */
public class ForeignKey implements IRenderable {
    private String name;
    private Set<String> localKeys = new HashSet<String>();
    private Set<String> foreignKeys = new HashSet<String>();
    private String foreignTable;
    private ReferenceOption referenceOption;

    public ForeignKey() { }

    public void setName( String name ) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void addLocalKey( String key ) {
        this.localKeys.add(key);
    }

    public Set<String> getLocalKeys() {
        return this.localKeys;
    }

    public void addForeignKey( String key ) {
        this.foreignKeys.add(key);
    }

    public Set<String> getForeignKeys() {
        return this.foreignKeys;
    }

    public void setForeignTable( String tblName ) {
        this.foreignTable = tblName;
    }

    public String getForeignTable() {
        return this.foreignTable;
    }

    public void setReferenceOption( ReferenceOption option ) {
        this.referenceOption = option;
    }

    public ReferenceOption getReferenceOption() {
        return this.referenceOption;
    }

}
