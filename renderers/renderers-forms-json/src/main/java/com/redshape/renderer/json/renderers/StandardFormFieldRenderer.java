package com.redshape.renderer.json.renderers;

import com.redshape.form.IFormField;
import com.redshape.renderer.IRenderersFactory;

import java.util.Collection;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.renderer.json.renderers
 * @date 3/1/12 {2:59 PM}
 */
public class StandardFormFieldRenderer extends AbstractJSONRenderer<IFormField> {

    public StandardFormFieldRenderer( IRenderersFactory renderersFactory ) {
        super(renderersFactory);
    }

    @Override
    public void repaint(IFormField renderable) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String render(IFormField renderable) {
        StringBuilder builder = new StringBuilder();
        builder.append(
                this.createObject(
                        this.createField("id", "\"" + renderable.getId() + "\""),
                        this.createField("name", "\"" + renderable.getCanonicalName() + "\""),
                        this.createField("messages", this.getRenderersFactory()
                                .forEntity(Collection.class)
                                .render(renderable.getMessages())),
                        this.createField("valid", renderable.isValid())
                )
        );

        return builder.toString();
    }
}
