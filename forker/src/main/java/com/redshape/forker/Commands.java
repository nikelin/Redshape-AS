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
    
    public static final long PAUSE = 0x00001L;
    public static final long PAUSE_RSP = 0x10001L;
    public static final long RESUME = 0x00002L;
    public static final long RESUME_RSP = 0x10002L;
    public static final long FIND_RESOURCE = 0x00003L;
    public static final long FIND_RESOURCE_RSP = 0x10003L;
    public static final long FIND_RESOURCES = 0x00004L;
    public static final long FIND_RESOURCES_RSP = 0x10004L;
    public static final long RESOLVE_CLASS = 0x00005L;
    public static final long RESOLVE_CLASS_RSP = 0x10005L;
    public static final long SHUTDOWN = 0x00006L;
    public static final long SHUTDOWN_RSP = 0x10006L;
    public static final long GET_RUNNING_STATE = 0x00007L;
    public static final long GET_RUNNING_STATE_RSP = 0x10007L;
    public static final long ERROR_RSP = 0x11007L;
    public static final long INIT_RSP = 0x22008L;
    
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

        REGISTRY.put( commandId, commandClazz );
        log.info("Fork command class " + commandClazz.getCanonicalName() + " registered as ID#" + commandId);
    }
    
}
