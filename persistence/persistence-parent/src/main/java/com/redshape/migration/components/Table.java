package com.redshape.migration.components;

import java.util.*;

/**
 * WebCam Project
 *
 * @author nikelin
 * @project vio
 * @package com.vio.migration.components
 * @date Apr 6, 2010
 */
public class Table {
    private boolean isTemporary;
    private String name;
    private Set<Field> fields = new HashSet<Field>();
    private Set<ForeignKey> foreignKeys = new HashSet<ForeignKey>();
    private Set<Index> indexes = new HashSet<Index>();
    private Set<UniqueConstraint> uniqueConstrains = new HashSet<UniqueConstraint>();
    private Set<TableOption> options = new HashSet<TableOption>();

    public Table() {}

    public Table( String name ) {
        this( name, new Field[] {} );
    }

    public Table(
        String name,
        Field[] fields
    ) {
        this(name, fields, new ForeignKey[] {} );
    }

    public Table(
        String name,
        Field[] fields,
        ForeignKey[] foreignKeys
    ) {
        this( name, fields, foreignKeys, new Index[] {} );
    }

    public Table(
        String name,
        Field[] fields,
        ForeignKey[] foreignKeys,
        Index[] indexes
    ) {
        this( name, fields, foreignKeys, indexes, new UniqueConstraint[] {} );
    }

    public Table(
        String name,
        Field[] fields,
        ForeignKey[] foreignKeys,
        Index[] indexes,
        UniqueConstraint[] constraints
    ) {
        this( name, fields, foreignKeys, indexes, constraints, new TableOption[] {} );
    }

    public Table(
        String name,
        Field[] fields,
        ForeignKey[] foreignKeys,
        Index[] indexes,
        UniqueConstraint[] constraints,
        TableOption[] options
    ) {
        this(
            name,
            new HashSet( Arrays.asList(fields) ),
            new HashSet( Arrays.asList( foreignKeys ) ),
            new HashSet( Arrays.asList(indexes) ),
            new HashSet( Arrays.asList(constraints) ),
            new HashSet( Arrays.asList(options) )
        );
    }

    public Table(
        String name,
        Set<Field> fields,
        Set<ForeignKey> foreignKeys,
        Set<Index> indexes,
        Set<UniqueConstraint> constraints,
        Set<TableOption> options
    ) {
        this.name = name;
        this.fields = fields;
        this.foreignKeys = foreignKeys;
        this.indexes = indexes;
        this.uniqueConstrains = constraints;
        this.options = options;
    }


    public void setTemporary( boolean isTemporary ) {
        this.isTemporary = isTemporary;
    }

    public boolean isTemporary() {
        return this.isTemporary;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void addField( Field field ) {
        this.fields.add(field);
    }

    public void removeField( Field field ) {
        this.fields.remove(field);
    }

    public Set<Field> getFields() {
        return this.fields;
    }

    public void addForeignKey( ForeignKey key ) {
        this.foreignKeys.add(key);
    }

    public void addUniqueConstrain( UniqueConstraint constraint ) {
        this.uniqueConstrains.add(constraint);
    }

    public Set<TableOption> getOptions() {
        return this.options;
    }

    public void addOption( TableOption option ) {
        this.options.add(option);
    }

}
