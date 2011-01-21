package com.redshape.io.net.discovery.nmap;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.redshape.io.INetworkNode;
import com.redshape.io.net.discovery.DiscoveryException;
import com.redshape.io.net.discovery.INetworkDiscoveryService;
import com.redshape.io.net.discovery.nmap.analyzers.NMapOutputAnalyzer;
import com.redshape.utils.StringUtils;

/**
 * Network discovery implementation based on abilities of NMap
 *
 * @important @todo: It's will be great to generalize this entity to something like IEnvironmentInteractor
 * @todo: It's would be magnificently to reduce dependency from system environment
 * and interacts with NMap from JNI or implements NMap on Java otherwise.
 *
 * @author nikelin
 */
public class NMapDiscoveryService implements INetworkDiscoveryService {
    private static final Logger log = Logger.getLogger( NMapDiscoveryService.class );

    /**
        * Path to nmap executable
        */
    private String executablePath;

    /**
       * NMap output analyzer
       */
    private NMapOutputAnalyzer analyzer;

    /**
        * Will be removed in the next version (currently handle arguments in a plain views (string array) )
        */
    private String[] nmapArgs = new String[] {};

    private Map<String,  String> attributes = new HashMap();

    public Collection<INetworkNode> service() throws DiscoveryException {
        try {
            this.checkConfig();

            String command = this.prepareCommand();
            log.info( command );
            log.info("Path to NMap: " + this.getExecutablePath() );
            log.info("Arguments: " + Arrays.asList( this.createConfiguration() ) );
            Process process = Runtime.getRuntime().exec( command );
            // process termination was abnormal
            try {
                process.waitFor();
            } catch ( InterruptedException e ) {
                if ( process.exitValue() != 0 ) {
                    throw new DiscoveryException();
                }
            }

            BufferedReader outputReader = new BufferedReader( new InputStreamReader( process.getInputStream() ) );
            BufferedReader errorsReader = new BufferedReader( new InputStreamReader( process.getErrorStream() ) );
            StringBuilder data = new StringBuilder();
            String buff;

            StringBuilder errorsBuff = new StringBuilder();
            while ( null != ( buff = errorsReader.readLine() ) ) {
                errorsBuff.append( buff );
            }

            String error = errorsBuff.toString();
            if ( !error.isEmpty() ) {
                throw new DiscoveryException( error.toString() );
            }

            while ( null != ( buff = outputReader.readLine() ) ) {
                data.append( buff );
            }

            return this.getAnalyzer().analyze( data.toString() );
        } catch ( Throwable e ) {
            log.error( e.getMessage(), e );
            throw new DiscoveryException( e.getMessage() );
        }
    }

    public String prepareCommand() {
        return new StringBuilder().append( this.getExecutablePath() )
                           .append(" ")
                           .append(
                                StringUtils.join( this.createConfiguration(), " " )
                           ).toString();
    }

    @Deprecated
    /**
         * @TODO: Will be removed in the next versions
         */
    public void setNmapArgs( String[] args ) {
        this.nmapArgs = args;
    }

    @Deprecated
    /**
         * @TODO: Will be removed in the next versions
         */
    public String[] getNmapArgs() {
        return this.nmapArgs;
    }

    /**
         * Return configuration array to send o
         * @return
         */
    protected String[] createConfiguration() {
        return this.nmapArgs;
//
//        String[] config = new String[this.getAttributes().size()];
//
//        int i = 0;
//        for ( String key : this.getAttributes().keySet() ) {
//            config[i++] = key + this.getAttribute(key);
//        }
//
//        return config;
    }

    public Map<String,  String> getAttributes() {
        return this.attributes;
    }

    public String getAttribute( String name ) {
        return this.attributes.get(name);
    }

    public void setAttribute( String name, String value ) {
        if ( !this.isSupported(name) ) {
            throw new IllegalArgumentException();
        }

        this.attributes.put(name, value);
    }

    /**
         * NMap executable path setter
         * @param path
         */
    public void setExecutablePath( String path ) {
        this.executablePath = path;
    }

    /**
         * NMap executable path getter :-)
         * @return
         */
    public String getExecutablePath() {
        return this.executablePath;
    }

    public void setAnalyzer( NMapOutputAnalyzer analyzer ) {
        this.analyzer = analyzer;
    }

    public NMapOutputAnalyzer getAnalyzer() {
        return this.analyzer;
    }

    /**
         * Check consistency and validity taken attributes
         * @TODO: needs to implement... :-)
         * @return
         */
    public boolean checkConfig() {
        return true;
    }

    /**
         * Check for support of given attribute name
         * @TODO: needs to implement... :-)
         * @param name
         * @return
         */
    public boolean isSupported( String name ) {
        return true;
    }

}
