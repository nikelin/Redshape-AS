package com.redshape.servlet.form.fields;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 13.06.11
 * Time: 1:15
 * To change this template use File | Settings | File Templates.
 */
public class TextAreaField extends AbstractField<String> {

    public TextAreaField() {
        this(null);
    }

    public TextAreaField(String id) {
        this(id, null);
    }

    public TextAreaField(String id, String name) {
        super(id, name);
    }

}
