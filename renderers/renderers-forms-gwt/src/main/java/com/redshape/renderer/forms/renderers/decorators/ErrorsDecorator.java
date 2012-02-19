package com.redshape.renderer.forms.renderers.decorators;

import com.google.gwt.user.client.ui.*;
import com.redshape.form.IForm;
import com.redshape.form.IFormField;
import com.redshape.form.IFormItem;
import com.redshape.form.decorators.DecoratorAttribute;
import com.redshape.form.decorators.Placement;
import com.redshape.utils.Commons;
import com.redshape.utils.validators.result.IValidationResult;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ErrorsDecorator extends AbstractDecorator {
    public static final String PLACEMENT = "Attribute.PLACEMENT";

    public static class Attributes extends DecoratorAttribute {

        protected Attributes(String name) {
            super(name);
        }

        public static final Attributes Placement = new Attributes("DecoratorAttributes.Errors.Placement");
    }

    protected void buildList( Collection<String> messages, Panel builder ) {
        boolean first = true;
        Tree errorsTree = new Tree();
        TreeItem root;
        errorsTree.addItem( root = new TreeItem("Errors") );

        for ( String message : messages ) {
            root.addItem( new TreeItem(message) );
        }
        
        builder.add(errorsTree);
    }

    protected Collection<String> buildMessagesList( IFormItem item ) {
        if ( item instanceof IForm ) {
            return this.buildMessagesList( (IForm) item );
        } else if ( item instanceof IFormField ) {
            return this.buildMessagesList( (IFormField<?>) item );
        } else {
            return new ArrayList<String>();
        }
    }

    protected Collection<String> buildMessagesList( IFormField<?> field ) {
        List<String> messages = new ArrayList<String>();
        for ( IValidationResult result : field.getValidationResults() ) {
            if ( !result.isValid() ) {
                messages.add( result.getMessage() );
            }
        }

        return messages;
    }

    protected Collection<String> buildMessagesList( IForm form ) {
        return form.getMessages();
    }

    @Override
    public Widget decorate(IFormItem item, Widget data) {
        Collection<String> messages = this.buildMessagesList(item);
        if ( messages.isEmpty() ) {
            return data;
        }

        VerticalPanel wrapper = new VerticalPanel();

        Placement placement = Commons.select( this.<Placement>getAttribute( Attributes.Placement ),
                Placement.AFTER );
        switch (placement) {
            case BEFORE:
                this.buildList( messages, wrapper );
                wrapper.add(data);
                break;
            case AFTER:
            case WRAPPED:
            default:
                wrapper.add(data);
                this.buildList( messages, wrapper );
                break;
        }

        return wrapper;
    }

}
