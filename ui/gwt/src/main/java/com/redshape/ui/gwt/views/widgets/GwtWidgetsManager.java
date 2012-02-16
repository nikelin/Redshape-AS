package com.redshape.ui.gwt.views.widgets;

import com.redshape.ui.utils.UIConstants;
import com.redshape.ui.views.widgets.IWidget;
import com.redshape.ui.views.widgets.IWidgetsManager;

import java.util.*;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.ui.gwt.views.widgets
 * @date 2/10/12 {4:00 PM}
 */
public class GwtWidgetsManager implements IWidgetsManager {
    private Map<UIConstants.Area, Collection<IWidget>> registry =
            new HashMap<UIConstants.Area, Collection<IWidget>>();
    
    @Override
    public void registerWidget(UIConstants.Area placement, IWidget widget) {
        Collection<IWidget> widgets;
        if ( this.registry.containsKey(placement ) ) {
            widgets = registry.get(placement);
        } else {
            registry.put( placement, widgets = new ArrayList<IWidget>() );
        }
        
        widgets.add(widget);
    }

    @Override
    public Map<UIConstants.Area, Collection<IWidget>> getWidgets() {
        return this.registry;
    }

    @Override
    public Collection<IWidget> getWidgets(UIConstants.Area placement) {
        return this.registry.get(placement);
    }

    @Override
    public void setWidgets(Map<UIConstants.Area, Collection<IWidget>> widgets) {
        this.registry = widgets;
    }
}
