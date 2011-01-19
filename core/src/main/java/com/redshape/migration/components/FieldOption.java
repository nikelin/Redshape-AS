package com.redshape.migration.components;

import com.redshape.render.IRenderable;

/**
 * WebCam Project
 *
 * @author nikelin
 * @project vio
 * @package com.vio.migration.components
 * @date Apr 6, 2010
 */
public class FieldOption implements IRenderable {
    private FieldOptions option;
    private String value;

    public FieldOption() {}

    public FieldOption( FieldOptions option ) {
        this( option, null );
    }
    
    public FieldOption( FieldOptions option, String value ) {
        this.option = option;
        this.value = value;
    }

    public FieldOptions getOption() {
        return this.option;
    }

    public void setOption( FieldOptions option ) {
        this.option = option;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue( String value ) {
        this.value = value;
    }
}
