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
