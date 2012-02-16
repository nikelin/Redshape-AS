package com.redshape.form.fields;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 16.06.11
 * Time: 23:22
 * To change this template use File | Settings | File Templates.
 */
public class LabelField extends AbstractField<String> {

    public LabelField() {
        this(null);
    }

    public LabelField( String id ) {
        this(id, null);
    }

    public LabelField( String id, String name ) {
        super(id, name);
    }

}
