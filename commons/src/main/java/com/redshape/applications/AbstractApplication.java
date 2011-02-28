package com.redshape.applications;

import com.redshape.applications.bootstrap.IBootstrap;
import com.redshape.utils.PackagesLoader;
import com.redshape.utils.ResourcesLoader;
import com.redshape.utils.config.IConfig;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Основной класс приложения
 *
 * Класс реализует основную логику запуска и остановки приложения сервера, а так же
 * запуска бутстраппера ( {@see com.vio.Bootstrap} ) и регистрации доступных API_ID-интерфейсов.
 *
 * @author nikelin
 * @group Server
 * @FIXME Отрефакторить и разбить на несколько абстрактных классов
 */
public abstract class AbstractApplication implements IApplication, ISingletonApplication {
	private static final Logger log = Logger.getLogger( AbstractApplication.class );
    
    public static boolean normalFinalization = true;
    public static Map<String, String> env = new HashMap<String, String>();

    public enum State {
        RUNNING,
        STOPPED
    }

    private class AppShutdownHook extends Thread {
        @Override
        public void run() {
            if ( AbstractApplication.this.pidCheckup && isNormalFinilized() ) {
                System.out.println("Termination signal received...");
                System.out.println("Reseting PID");

                try {
                    AbstractApplication.this.resetPid();
                } catch ( Throwable e ) {
                    e.printStackTrace();
                }
            }
        }
    }

    public enum Event {
        START,
        STOP
    }
    
    private IConfig config;
    
    @Autowired( required = true )
    private ResourcesLoader resourcesLoader;
    
    @Autowired( required = true )
    private PackagesLoader packagesLoader;

    private boolean pidCheckup;
    
    private Integer version;

    private IBootstrap boot;

    private State state;

    private String[] envArgs;

    public AbstractApplication( String[] args ) throws ApplicationException {
        this.envArgs = args;

        this.initEnv(this.envArgs);
    }
    
    public void setPackagesLoader( PackagesLoader loader ) {
    	this.packagesLoader = loader;
    }
    
    protected PackagesLoader getPackagesLoader() {
    	return this.packagesLoader;
    }
    
    public void setResourcesLoader( ResourcesLoader loader ) {
    	this.resourcesLoader = loader;
    }
    
    public ResourcesLoader getResourcesLoader() {
    	return this.resourcesLoader;
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
        
        Runtime.getRuntime().addShutdownHook( new AppShutdownHook() );

        if ( this.isPidCheckup() ) {
            try {
                actualizePid();
            } catch ( IOException e ) {
                log.error("Cannot update current process PID", e);
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
    
    public void setBootstrap( IBootstrap bootstrap ) {
    	this.boot = bootstrap;
    }
    
    protected IBootstrap getBootstrap() {
        return this.boot;
    }

    public static String getLocalScope( Class<?> clazz ) {
        File file = new File( clazz.getProtectionDomain().getCodeSource().getLocation().getPath() );
        if ( !file.isDirectory() ) {
            return file.getParentFile().getPath();
        }

        return file.getPath();
    }

    public static boolean isUnderJar( String path ) {
        return path.endsWith(".jar");
    }

    private void resetPid() throws IOException {
        File file = this.getResourcesLoader().loadFile(".pid");
        if ( file != null ) {
            file.delete();
        }
    }

    private static boolean isNormalFinilized() {
        return normalFinalization == true;
    }

    private void actualizePid() throws IOException {
        File file = getPidFile();

        if ( !checkPidFile(file) ) {
            System.out.println("Another server instance already runs...");
            System.out.println("Exiting...");

            normalFinalization = false;
            System.exit(0);
        }

        this.updatePidFile(file);
    }

    private File getPidFile() throws IOException {
        File pidFile = this.getResourcesLoader().loadFile( this.getClass().getResource("./.pid").toString(), false );
        if ( !pidFile.exists() ) {
            pidFile.createNewFile();
        }

        return pidFile;
    }

    private boolean checkPidFile( File file ) throws IOException {
        if ( file.exists() ) {
            String data = this.getResourcesLoader().loadData(file);
            System.out.println("Current pid: " + data );
            if ( !data.isEmpty() && !data.equals( String.valueOf( getPid() ) ) ) {
                return false;
            }
        }

        return true;
    }

    protected void updatePidFile( File file ) throws IOException {
        if ( !file.exists() ) {
            file.createNewFile();
        }

        FileWriter writer = new FileWriter( file );
        writer.write( String.valueOf( getPid() ) );
        writer.close();
    }

    public Integer getPid() throws IOException {
        Process pidRequest = Runtime.getRuntime().exec( this.getResourcesLoader().loadFile("get-pid").getAbsolutePath() );
        BufferedReader reader = new BufferedReader( new InputStreamReader( pidRequest.getInputStream() ) );

        return  Integer.parseInt( reader.readLine() );
    }

    /**
     * Запуск приложения ( активация сервера, скедулера, т.д. )
     *
     * @throws ApplicationException
     * @return void
     */
    abstract public void start() throws ApplicationException;
    
    public void stop() {
    	//...
    }

    protected void setState( State state ) {
        this.state = state;
    }

    public boolean isRunning() {
        return this.state == State.RUNNING;
    }

    public Integer getCurrentVersion() {
        if ( this.version == null ) {
            try {
                String _version = this.getResourcesLoader().loadData(".version", true);
                this.version = Integer.valueOf( _version.replaceAll("\n", "") );
            } catch (Throwable e ) {
                log.error( e.getMessage(), e );
                this.version = 1;
            }
        }

        return this.version;
    }

    public boolean isPidCheckup() {
        return this.pidCheckup;
    }

    public void setPidCheckup( boolean bool ) {
        this.pidCheckup = bool;
    }

}

