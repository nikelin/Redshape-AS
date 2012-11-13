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

package com.redshape.applications;

import com.redshape.utils.config.IConfig;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Main application abstraction
 * 
 * @author Cyril <nikelin> Karpenko <self@nikelin.ru>
 */
public abstract class AbstractApplication implements IApplication {
	private static final Logger log = Logger.getLogger( AbstractApplication.class );
    public static Map<String, String> env = new HashMap<String, String>();

    private static int PID = -1;

    private IConfig config;

    private String[] envArgs;

    public AbstractApplication( String[] args ) throws ApplicationException {
        this.envArgs = args;

        this.initEnv(this.envArgs);
    }

    protected void initEnv( String[] args ) throws ApplicationException {
        for ( String arg : args ) {
            if ( arg.startsWith("--") ) {
                String[] parts = arg.substring(2).split("=");
                if ( parts.length > 0 ) {
                    env.put( parts[0], parts[1] );
                }
            }
        }
    }

    public String[] getEnvArgs() {
        return this.envArgs;
    }

    public void setEnvArg( String name, String value ) {
        env.put( name, value );
    }

    public String getEnvArg( String name ) {
        return env.get(name);
    }

    public void setConfig( IConfig config ) {
    	this.config = config;
    }
    
    public IConfig getConfig() {
    	return this.config;
    }

	@Override
    public void stop() {
    	log.info("Application going to be stopped");
    }

    private static int requestPid() throws IOException {
        int result;

        Process process = Runtime.getRuntime().exec( new String[] { "bash", "-c", "echo $PPID"} );

        try {
            process.waitFor();
        } catch ( InterruptedException e ) {
            throw new IllegalStateException( e.getMessage(), e );
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        result = Integer.parseInt( reader.readLine() );
        process.destroy();
        return result;
    }

    public static int pid() {
        try {
            if ( PID < 0 ) {
                PID = requestPid();
            }

            return PID;
        } catch ( IOException e ) {
            throw new IllegalStateException( e.getMessage(), e );
        }
    }

}

