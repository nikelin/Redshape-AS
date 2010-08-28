package com.vio.applications;

import com.vio.applications.bootstrap.IBootstrap;
import com.vio.config.Config;
import com.vio.config.IConfig;
import com.vio.config.readers.ConfigReaderException;
import com.vio.persistence.entities.SystemState;
import com.vio.persistence.managers.ManagerException;
import com.vio.utils.Registry;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

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
public abstract class Application implements IApplication, ISingletonApplication {
    static {
        String log4jConfigPath = System.getProperty("log4j.configuration");

        PropertyConfigurator.configure(
             log4jConfigPath == null ?  "log4j.properties" : log4jConfigPath
        );
    }
    
    private static final Logger log = Logger.getLogger( Application.class );
    
    public static boolean normalFinalization = true;
    public static Map<String, String> env = new HashMap<String, String>();

    private String rootDirectory;

    public enum State {
        RUNNING,
        STOPPED
    }

    private class AppShutdownHook extends Thread {
        @Override
        public void run() {
            if ( Application.this.pidCheckup && isNormalFinilized() ) {
                System.out.println("Termination signal received...");
                System.out.println("Reseting PID");

                try {
                    Application.this.resetPid();
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

    private boolean pidCheckup = false;

    private Integer version;

    /**
     * Бутстраппер приложения (инициализация базового состояния)
     *
     * @see com.vio.applications.bootstrap.Bootstrap
     * @var Bootstrap
     */
    private IBootstrap boot;

    private State state;

    private String[] envArgs;

    private Class<?> appContext;

    public Application( Class<?> appContext, String[] args, IBootstrap bootstrap ) throws ApplicationException {
        this.envArgs = args;
        this.boot = bootstrap;
        this.appContext = appContext;

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

        Registry.setApplication(this);
        Registry.setRootDirectory( getLocalScope( this.appContext ) );

        try {
            Registry.setConfig( this.createConfig() );
        } catch ( Throwable e ) {
            log.error( e.getMessage(), e );
            throw new ApplicationException();
        }

        Registry.setResourcesDirectory( this.getResourcesDir() );


        Registry.addClasspathEntry( getResourcesDir() );
        Registry.addClasspathEntry("./");

        try {
            Registry.processLibrariesPath( this.getEnvArg("libsPath") == null
                                                            ? Registry.getServerConfig().getLibrariesPath()
                                                            : this.getEnvArg("libsPath") );
        } catch ( Throwable e ) {
            log.error( e.getMessage(), e );
            log.error("Libraries loading exception");
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

    public String getEnvArg( String name ) {
        return env.get(name);
    }

    public boolean isProduction() {
        return this.getExecutionMode() == "common";
    }

    public IBootstrap getBootstrap() {
        return this.boot;
    }

    public String getExecutionMode() {
        return this.getEnvArg("executionMode") == null ? "common" : this.getEnvArg("executionMode");
    }

    protected String getResourcesDir() {
        return this.getEnvArg("dataPath") != null ? this.getEnvArg("dataPath") : Registry.getRootDirectory() + "/resources" ;
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

    public static boolean isUnderJar() {
        return isUnderJar( Registry.getRootDirectory() );
    }

    public static void exit() {
        System.exit(0);
    }

    private void resetPid() throws IOException {
        File file = Registry.getResourcesLoader().loadFile(".pid");
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

            exit();
        }

        updatePidFile(file);
    }

    private File getPidFile() throws IOException {
        File pidFile = Registry.getResourcesLoader().loadFile( this.getResourcesDir() + "/.pid", false );
        if ( !pidFile.exists() ) {
            pidFile.createNewFile();
        }

        return pidFile;
    }

    private boolean checkPidFile( File file ) throws IOException {
        if ( file.exists() ) {
            String data = Registry.getResourcesLoader().loadData(file);
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
        Process pidRequest = Runtime.getRuntime().exec( Registry.getResourcesLoader().loadFile("get-pid").getAbsolutePath() );
        BufferedReader reader = new BufferedReader( new InputStreamReader( pidRequest.getInputStream() ) );

        return  Integer.parseInt( reader.readLine() );
    }

    /**
     * Запуск приложения ( активация сервера, скедулера, т.д. )
     *
     * @throws ApplicationException
     * @return void
     */
    public void start() throws ApplicationException {
        try {
            this.boot.init();
        } catch ( Throwable e ) {
            e.printStackTrace();
            throw new ApplicationException("Startup error!");
        }
    }

    /**
     * Остановка приложения (сервер, скедулера, etc. )
     *
     * @return void
     */
    public void stop() {
        Application.exit();
    }

    protected void setState( State state ) {
        this.state = state;
    }

    public boolean isRunning() {
        return this.state == State.RUNNING;
    }

    protected IConfig createConfig() throws IOException,  ConfigReaderException {
        File file = Registry.getResourcesLoader().loadFile( this.getResourcesDir() + File.separator + Config.BOOTSTRAP_CONFIG_PATH, false );

        if ( file.exists() ) {
            return new Config(file);
        }

        return new Config();
    }

    public Integer getCurrentVersion() {
        if ( this.version == null ) {
            try {
                String _version = Registry.getResourcesLoader().loadData(".version", true);
                log.info("Current system version: " + SystemState.getInstance().getVersion() + "; actual version: " + _version );
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

