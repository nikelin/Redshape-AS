package com.redshape.applications.bootstrap.actions;

import com.redshape.applications.bootstrap.AbstractBootstrapAction;
import com.redshape.applications.bootstrap.Action;
import com.redshape.daemons.HeapMonitorTask;
import com.redshape.daemons.PluginsLoaderTask;
import com.redshape.utils.Constants;
import com.redshape.utils.Registry;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 22, 2010
 * Time: 2:51:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class MonitorsInit extends AbstractBootstrapAction {
    public MonitorsInit() {
        this.setId( Action.MONITORS_ID);
        this.addDependency( Action.API_ID );
        this.markCritical();
    }

    public void process() {
        Registry.getTicker().scheduleAtFixedRate( new HeapMonitorTask(), Constants.TIME_SECOND * 5, HeapMonitorTask.PERIOD );
        Registry.getTicker().scheduleAtFixedRate( new PluginsLoaderTask(), Constants.TIME_SECOND * 5, PluginsLoaderTask.PERIOD );
    }

}
