package com.redshape.ui.gwt;

import com.redshape.ui.application.AbstractApplication;
import com.redshape.ui.application.ApplicationException;
import com.redshape.ui.application.IBeansProvider;
import com.redshape.ui.components.IComponent;
import com.redshape.ui.utils.UIConstants;
import com.redshape.ui.views.widgets.IWidget;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.ui.gwt
 * @date 2/8/12 {1:19 PM}
 */
public class GWTApplication extends AbstractApplication {

    public GWTApplication(IBeansProvider applicationContext)
            throws ApplicationException {
        super(applicationContext);
    }

    @Override
    protected void initComponent(IComponent component) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    protected void initWidget(UIConstants.Area area, IWidget widget) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

}
