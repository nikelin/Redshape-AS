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

package com.redshape.utils.net;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;

public class Utils {
	public static Integer MIN_PORT_NUMBER = 1024;
	public static Integer MAX_PORT_NUMBER = Short.MAX_VALUE * 2;
	
	public static int getAvailable( int port ) throws IOException {
		do {
			port++;
		} while ( !available(port) );
		
		return port;
	}
	
	public static boolean available(int port) throws IOException {
	    if (port < MIN_PORT_NUMBER || port > MAX_PORT_NUMBER) {
	        throw new IllegalArgumentException("Invalid start port: " + port);
	    }

	    ServerSocket ss = null;
	    DatagramSocket ds = null;
	    try {
	        ss = new ServerSocket(port);
	        ss.setReuseAddress(true);
	        ds = new DatagramSocket(port);
	        ds.setReuseAddress(true);
	        return true;
	    } catch ( Throwable e ) {
            return false;
	    } finally {
            if (ds != null) {
                ds.close();
            }

            if (ss != null) {
                ss.close();
            }
        }
	}

}
