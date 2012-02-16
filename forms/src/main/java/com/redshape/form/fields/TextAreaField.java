package com.redshape.form.fields;

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
