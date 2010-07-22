package com.vio.exceptions;

import com.vio.config.readers.ConfigReaderException;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import com.vio.utils.Registry;
import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jan 18, 2010
 * Time: 6:09:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class ErrorCodes {
    private static final Logger log = Logger.getLogger( ErrorCodes.class );
    private static Properties errors;
    private static boolean isLoaded = false;

    protected static void loadMessages() throws IOException {
        errors = new Properties();
        errors.load( new FileInputStream( Registry.getResourcesLoader().loadFile("data/errors.properties") ) );
    }

    public static Properties getMessages() {
        return errors;
    }

    public static String getMessage( ErrorCode code ) throws ConfigReaderException, IOException {
        if ( !isLoaded ) {
            try {
                loadMessages();
            } catch ( Throwable e ) {
                log.error( e.getMessage(), e );
            }

            isLoaded = true;
        }

        return errors.getProperty( String.valueOf( code.name() ) );    
    }
    
}
