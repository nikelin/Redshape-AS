package com.redshape.daemons;

import com.redshape.utils.Constants;
import org.apache.log4j.Logger;

import java.util.TimerTask;

/**
 * WebCam Project
 *
 * @author nikelin
 * @project vio
 * @package com.vio.daemons
 * @date Apr 15, 2010
 */
public class HeapMonitorTask extends TimerTask {
    public final static Integer PERIOD = Constants.TIME_MINUTE * 5;
    private static final Logger log = Logger.getLogger( HeapMonitorTask.class );

    @Override
    public void run() {
        log.info("Current memory stats: \n free " + Runtime.getRuntime().freeMemory() / 1000 + "KB \n total: " + Runtime.getRuntime().totalMemory() / 1000 + "KB" );
        log.info("Performing GC...");

        System.gc();
    }

}
