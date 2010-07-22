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
public class TableOption implements Renderable {
    private TableOptions option;
    private String value;

    public TableOption() {}

    public TableOption( TableOptions option ) {
        this(option, null);
    }

    public TableOption( TableOptions option, String value ) {
        this.option = option;
        this.value = value;
    }

    public void setOption( TableOptions option ) {
        this.option = option;
    }

    public TableOptions getOption() {
        return this.option;
    }

    public void setValue( String value ) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

}
