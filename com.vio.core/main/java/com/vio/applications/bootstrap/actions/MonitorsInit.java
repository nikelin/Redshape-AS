package com.vio.applications.bootstrap.actions;

import com.vio.applications.bootstrap.AbstractBootstrapAction;
import com.vio.applications.bootstrap.Action;
import com.vio.daemons.GarbageKillerTask;
import com.vio.daemons.HeapMonitorTask;
import com.vio.daemons.PluginsLoaderTask;
import com.vio.utils.Constants;
import com.vio.utils.Registry;

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
        Registry.getTicker().scheduleAtFixedRate( new GarbageKillerTask(), Constants.TIME_SECOND * 5, GarbageKillerTask.PERIOD );
        Registry.getTicker().scheduleAtFixedRate( new PluginsLoaderTask(), Constants.TIME_SECOND * 5, PluginsLoaderTask.PERIOD );
    }

}
