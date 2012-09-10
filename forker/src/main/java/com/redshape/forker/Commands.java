package com.redshape.forker;

import com.redshape.forker.commands.*;
import com.redshape.utils.Commons;
import org.apache.log4j.Logger;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.forker.commands
 * @inspiredBy Jukka Zitting <jukkaz.wordpress.com>
 * @date 1/31/12 {4:39 PM}
 */
public final class Commands {

    private static final Logger log = Logger.getLogger(Commands.class);

    private static long LAST_USED_ID = 0;

    public static final long PAUSE = nextID(0x00001);
    public static final long PAUSE_RSP = nextID();
    public static final long RESUME = nextID();
    public static final long RESUME_RSP = nextID();
    public static final long FIND_RESOURCE = nextID();
    public static final long FIND_RESOURCE_RSP = nextID();
    public static final long FIND_RESOURCES = nextID();
    public static final long FIND_RESOURCES_RSP = nextID();
    public static final long RESOLVE_CLASS = nextID();
    public static final long RESOLVE_CLASS_RSP = nextID();
    public static final long SHUTDOWN = nextID();
    public static final long SHUTDOWN_RSP = nextID();
    public static final long GET_RUNNING_STATE = nextID();
    public static final long GET_RUNNING_STATE_RSP = nextID();
    public static final long ERROR_RSP = nextID();
    public static final long INIT_RSP = nextID();

    public static final Map<Long, Class<?>> REGISTRY = new HashMap<Long, Class<?>>();
    static {
        REGISTRY.put( PAUSE, PauseCommand.Request.class );
        REGISTRY.put( PAUSE_RSP, PauseCommand.Response.class );
        REGISTRY.put( RESUME, ResumeCommand.Request.class );
        REGISTRY.put( RESUME_RSP, ResumeCommand.Response.class );
        REGISTRY.put( SHUTDOWN, ShutdownCommand.Request.class );
        REGISTRY.put( SHUTDOWN_RSP, ShutdownCommand.Response.class );
        REGISTRY.put( FIND_RESOURCE, FindResourceCommand.Request.class );
        REGISTRY.put( FIND_RESOURCE_RSP, FindResourceCommand.Response.class );
        REGISTRY.put( FIND_RESOURCES, FindResourcesCommand.Request.class );
        REGISTRY.put( FIND_RESOURCES_RSP, FindResourcesCommand.Response.class );
        REGISTRY.put( INIT_RSP, InitResponse.class );
        REGISTRY.put( ERROR_RSP, ErrorResponse.class );
    }

    private static long nextID( long prev ) {
        return prev++;
    }

    public static long nextID() {
        return nextID(LAST_USED_ID);
    }

    public static <T extends IForkCommandResponse> T createResponse( Long commandId )
            throws InstantiationException {
        return createResponse(commandId, null);
    }
    
    public static <T extends IForkCommandResponse> T createResponse( Long commandId, IForkCommandResponse.Status status )
        throws InstantiationException {
        try {
            Commons.checkNotNull(commandId);

            Class<?> clazz = REGISTRY.get( commandId );
            if ( !( IForkCommandResponse.class.isAssignableFrom(clazz) ) ) {
                throw new IllegalArgumentException("Given commandId must point to the " +
                            IForkCommandResponse.class.getCanonicalName() + " type");
            }

            for ( Constructor<?> constructor : clazz.getConstructors() ) {
                if ( constructor.getParameterTypes().length == 1 ) {
                    return (T) constructor.newInstance( status );
                } else if ( constructor.getParameterTypes().length == 2 ) {
                    return (T) constructor.newInstance( commandId, status );
                } else if ( constructor.getParameterTypes().length == 0 ) {
                    return (T) constructor.newInstance();
                }
            }

            throw new IllegalStateException("Unable to instantiate response object");
        } catch ( Throwable e ) {
            throw new InstantiationException( e.getMessage() );
        }
    }
    
    public static <T extends IForkCommand> T createCommand( Long commandId )
            throws InstantiationException {
        try {
            Commons.checkNotNull(commandId);
            
            Class<?> clazz = REGISTRY.get( commandId );
            if ( !( IForkCommand.class.isAssignableFrom(clazz) ) ) {
                throw new IllegalArgumentException("Given commandId must point to the " +
                        IForkCommand.class.getCanonicalName() + " type");
            }

            return  (T) clazz.newInstance();
        } catch ( Throwable e ) {
            throw new InstantiationException( e.getMessage() );
        }
    }
    
    public static void register( Long commandId, Class<?> commandClazz ) {
        Commons.checkNotNull(commandClazz);
        Commons.checkNotNull(commandId);

        if ( REGISTRY.containsKey(commandId) ) {
            throw new IllegalArgumentException("Requested command ID already reserved by a "
                    + commandClazz.getCanonicalName() + " implementation");
        }

        REGISTRY.put( commandId, commandClazz );
        log.info("Fork command class " + commandClazz.getCanonicalName() + " registered as ID#" + commandId);
    }
    
}
