package com.redshape.renderer.json.renderers;

import com.redshape.form.IForm;
import com.redshape.form.IFormItem;
import com.redshape.renderer.IRenderersFactory;

import java.util.Collection;
import java.util.List;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.renderer.json.renderers
 * @date 3/1/12 {2:59 PM}
 */
public class StandardFormsRenderers extends AbstractJSONRenderer<IForm> {

    private IRenderersFactory renderersFactory;
    
    public StandardFormsRenderers( IRenderersFactory factory ) {
        this.renderersFactory = factory;
    }

    protected IRenderersFactory getRenderersFactory() {
        return renderersFactory;
    }

    @Override
    public void repaint(IForm renderable) {
    }

    protected String renderFields( List<IFormItem> items ) {
        StringBuilder result = new StringBuilder();
        result.append("[");
        int i = 0;
        for ( IFormItem item : items ) {
            result.append( this.getRenderersFactory()
                    .<IFormItem, String>forEntity(item)
                    .render(item) );
            
            if ( i++ != items.size() - 1 ) {
                result.append(",");
            }
        }
        result.append("]");

        return result.toString();
    }
    
    @Override
    public String render(IForm renderable) {
        StringBuilder builder = new StringBuilder();
        builder.append(this.createObject(
            this.createField("messages", this.getRenderersFactory()
                            .<Collection, String>forEntity(Collection.class)
                            .render(renderable.getMessages())),
            this.createField("valid", renderable.isValid() ),
            this.createField("fields", this.renderFields(renderable.getItems()) )
        ));
        
        return builder.toString();
    }
}
