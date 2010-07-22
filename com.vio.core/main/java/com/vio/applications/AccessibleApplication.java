package com.vio.applications;

import com.vio.applications.bootstrap.Bootstrap;
import com.vio.commands.CommandsFactory;
import com.vio.commands.ICommand;
import com.vio.commands.ExecutionException;
import com.vio.utils.Registry;
import org.apache.log4j.Logger;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Abstract realization of accessible application
 *
 * @author nikelin
 * @project vio
 * @package com.vio.applications
 * @date Apr 19, 2010
 */
public abstract class AccessibleApplication extends Application implements IAccessibleApplication {
    private static final Logger log = Logger.getLogger( AccessibleApplication.class );

    public AccessibleApplication( Class<?> context, String[] args, Bootstrap bootstrap ) throws ApplicationException {
        super( context, args, bootstrap );
    }

}
