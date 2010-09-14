package com.redshape.migration.components;

import com.redshape.render.IRenderable;

import java.util.*;

/**
 * WebCam Project
 *
 * @author nikelin
 * @project vio
 * @package com.vio.migration.components
 * @date Apr 6, 2010
 */
public class Field implements IRenderable {
    private String name;
    private FieldType type;
    private Set<FieldOption> options = new HashSet<FieldOption>();

    public Field() {}

    public Field( String name ) {
        this(name, null );
    }

    public Field( String name, FieldType type ) {
        this( name, type, new FieldOption[] {} );
    }

    public Field( String name, FieldType type, FieldOption[] options ) {
        this(name, type, new HashSet( Arrays.asList( options ) ) );
    }

    public Field( String name, FieldType type, Set<FieldOption> options ) {
        this.name = name;
        this.type = type;
        this.options = options;
    }

    public String getName() {
        return this.name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public FieldType getType() {
        return this.type;
    }

    public void setType( FieldType type ) {
        this.type = type;
    }

    public void addOption( FieldOption option ) {
        this.options.add(option);
    }

    public Set<FieldOption> getOptions() {
        return this.options;
    }

    public void setDefaultValue( String value ) {
        this.options.add( new FieldOption( FieldOptions.DEFAULT, value ) );
    }

}
