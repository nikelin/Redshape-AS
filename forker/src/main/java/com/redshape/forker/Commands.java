/*
 * Copyright 2012 Cyril A. Karpenko
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

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

    public static final Map<Long, Class<?>> REGISTRY = new HashMap<Long, Class<?>>();
    static {
        REGISTRY.put( PauseCommand.Request.ID, PauseCommand.Request.class );
        REGISTRY.put( PauseCommand.Response.ID, PauseCommand.Response.class );
        REGISTRY.put( ResumeCommand.Request.ID, ResumeCommand.Request.class );
        REGISTRY.put( ResumeCommand.Response.ID, ResumeCommand.Response.class );
        REGISTRY.put( ShutdownCommand.Request.ID, ShutdownCommand.Request.class );
        REGISTRY.put( ShutdownCommand.Response.ID, ShutdownCommand.Response.class );
        REGISTRY.put( FindResourceCommand.Request.ID, FindResourceCommand.Request.class );
        REGISTRY.put( FindResourceCommand.Response.ID, FindResourceCommand.Response.class );
        REGISTRY.put( FindResourcesCommand.Request.ID, FindResourcesCommand.Request.class );
        REGISTRY.put( FindResourcesCommand.Response.ID, FindResourcesCommand.Response.class );
        REGISTRY.put( InitResponse.ID, InitResponse.class );
        REGISTRY.put( ErrorResponse.ID, ErrorResponse.class );
    }

    public static <T extends IForkCommandResponse> T createResponse( Long commandId )
            throws InstantiationException {
        return createResponse(commandId, IForkCommandResponse.Status.SUCCESS);
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
                    + REGISTRY.get(commandId).getCanonicalName() + " implementation");
        }

        REGISTRY.put( commandId, commandClazz );

        log.info("Fork command class " + commandClazz.getCanonicalName() + " registered as ID#" + commandId);
    }
    
}
