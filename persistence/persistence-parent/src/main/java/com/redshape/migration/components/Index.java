package com.redshape.migration.components;

import com.redshape.renderer.Renderable;

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
@Renderable
public class Index  {
    private String name;
    private IndexType type;
    private Set<String> fields = new HashSet<String>();

    public void setName( String name ) {
        this.name = name;
    }

    public void setType( IndexType type ) {
        this.type = type;
    }

    public void addField( String field ) {
        this.fields.add(field);
    }
}