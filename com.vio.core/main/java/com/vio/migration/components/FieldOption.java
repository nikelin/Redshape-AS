package com.vio.migration.components;

import com.vio.render.Renderable;

/**
 * WebCam Project
 *
 * @author nikelin
 * @project vio
 * @package com.vio.migration.components
 * @date Apr 6, 2010
 */
public class FieldOption implements Renderable {
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
