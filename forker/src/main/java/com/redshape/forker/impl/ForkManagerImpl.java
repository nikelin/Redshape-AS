package com.redshape.forker.impl;

import com.redshape.forker.IFork;
import com.redshape.forker.IForkManager;
import com.redshape.forker.ProcessException;
import com.redshape.forker.handlers.IForkCommandExecutor;
import com.redshape.forker.handlers.IForkCommandHandler;
import com.redshape.forker.handlers.impl.StandardForkCommandExecutor;
import com.redshape.forker.protocol.IForkProtocol;
import com.redshape.forker.protocol.StandardProtocol;
import com.redshape.forker.protocol.processor.IForkProtocolProcessor;
import com.redshape.forker.protocol.processor.StandardForkProtocolProcessor;
import com.redshape.forker.protocol.queue.IProtocolQueueCreator;
import com.redshape.utils.Commons;
import com.redshape.utils.IResourcesLoader;
import com.redshape.utils.SimpleStringUtils;
import com.redshape.utils.StringUtils;
import com.redshape.utils.system.ISystemFacade;
import com.redshape.utils.system.processes.ISystemProcess;
import com.redshape.utils.system.scripts.IScriptExecutor;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.forker.impl
 * @inspiredBy Jukka Zitting <jukkaz.wordpress.com>
 * @date 1/31/12 {2:07 PM}
 */
public class ForkManagerImpl implements IForkManager {
    private static final Logger log = Logger.getLogger( ForkManagerImpl.class );

    private static final String MEMORY_LIMIT_PARAM = "-Xmx%sM";
    private static final String MEMORY_INITIAL_PARAM = "-Xms%sM";
    private static final String CLASSPATH_PARAM = "-cp %s";
    private static final String DEBUG_STRING = "-agentlib:jdwp=transport=dt_socket,server=n,address=%s:%d,suspend=y";

    private IResourcesLoader loader;
    private ISystemFacade facade;

    private IProtocolQueueCreator protocolQueueCreator;

    private Map<IFork, IForkCommandExecutor> executors = new HashMap<IFork, IForkCommandExecutor>();
    private Map<IFork, IForkProtocolProcessor> processors = new HashMap<IFork, IForkProtocolProcessor>();
    private List<IFork> registry = new ArrayList<IFork>();
    private List<String> classPath = new ArrayList<String>();

    private ExecutorService service;

    private String rootPath;
    private String jvmPath = "/usr/bin/java";
    private boolean debugMode;
    private int debugPort;
    private String debugHost;
    private int memoryLimit;
    private int memoryInitial;
    private int cpuLimit;

    public ForkManagerImpl( IProtocolQueueCreator queueCreator, ISystemFacade facade, IResourcesLoader loader) {
        Commons.checkNotNull(queueCreator);
        Commons.checkNotNull(facade);
        Commons.checkNotNull(loader);

        this.service = Executors.newFixedThreadPool(100);
        this.protocolQueueCreator = queueCreator;
        this.facade = facade;
        this.loader = loader;
    }

    public void setService(ExecutorService service) {
        Commons.checkNotNull(service);
        this.service = service;
    }

    @Override
    public IForkCommandExecutor getCommandsExecutor(IFork fork, boolean forceStart) {
        IForkCommandExecutor executor;
        this.executors.put( fork, executor = this.createCommandsExecutor(fork) );

        if ( forceStart ) {
            this.service.execute( executor );
        }

        return executor;
    }

    @Override
    public IForkCommandExecutor getCommandsExecutor(IFork fork) {
        return this.getCommandsExecutor(fork, true);
    }

    protected IForkCommandExecutor createCommandsExecutor( IFork fork ) {
        return new StandardForkCommandExecutor( this.getForkProcessor(fork), Commons.<IForkCommandHandler>set() );
    }

    @Override
    public void setDebugHost(String value) {
        this.debugHost = value;
    }

    @Override
    public void setDebugPort(int port) {
        Commons.checkArgument( port <= Short.MAX_VALUE);
        this.debugPort = port;
    }

    @Override
    public void enableDebugMode(boolean value) {
        this.debugMode = value;
    }

    @Override
    public void addClassPath(String[] path) {
        for ( String pathPart : path ) {
            if ( pathPart != null ) {
                this.addClassPath(pathPart);
            }
        }
    }

    public void addClassPath( String path ) {
        if ( !this.classPath.contains(path) ) {
            this.classPath.add(path);
        }
    }

    public List<String> getClassPath() {
        return classPath;
    }

    public void setClassPath(List<String> classPath) {
        this.classPath = classPath;
    }

    public String getRootPath() {
        return rootPath;
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    public String getJvmPath() {
        return jvmPath;
    }

    public void setJvmPath(String jvmPath) {
        this.jvmPath = jvmPath;
    }

    protected IResourcesLoader getLoader() {
        return this.loader;
    }

    protected ISystemFacade getFacade() {
        return this.facade;
    }

    public int getMemoryInitial() {
        return memoryInitial;
    }

    public void setMemoryInitial(int memoryInitial) {
        this.memoryInitial = memoryInitial;
    }

    public int getMemoryLimit() {
        return memoryLimit;
    }

    public void setMemoryLimit(int memoryLimit) {
        this.memoryLimit = memoryLimit;
    }

    public int getCpuLimit() {
        return cpuLimit;
    }

    public void setCpuLimit(int cpuLimit) {
        this.cpuLimit = cpuLimit;
    }

    protected String prepareClassName( Class<?> clazz ) {
        return StringUtils.preparePathByClass(clazz);
    }

    protected IScriptExecutor createJVMExecutor() {
        return this.getFacade().getConsole().createExecutor( this.getJvmPath() );
    }

    protected IScriptExecutor createForkExecutor( String path, String codeSource, String[] args ) {
        IScriptExecutor executor = this.createJVMExecutor();

        if ( this.debugMode ) {
            executor.addUnnamedParameter( String.format( DEBUG_STRING, this.debugHost,  this.debugPort) );
        }

        executor
            .addUnnamedParameter( String.format( CLASSPATH_PARAM, StringUtils.join(
                new String[] {
                    codeSource,
                    this.getClass().getProtectionDomain().getCodeSource().getLocation().toExternalForm(),
                    StringUtils.class.getProtectionDomain().getCodeSource().getLocation().toExternalForm(),
                    SimpleStringUtils.class.getProtectionDomain().getCodeSource().getLocation().toExternalForm()
                } , File.pathSeparator)
            ) )
            .addUnnamedParameter( String.format( MEMORY_INITIAL_PARAM, this.getMemoryInitial() ) )
            .addUnnamedParameter( String.format( MEMORY_LIMIT_PARAM, this.getMemoryLimit() ) )
            .addUnnamedParameter(path)
            .addUnnamedParameter( StringUtils.join(args, " ") );

        return executor;
    }

    protected String copyClazz( String path, Class<?> clazz ) throws IOException {
        File file = new File(path);
        if ( !file.isDirectory() || !file.canWrite() ) {
            throw new IOException("Inaccessible write destination");
        }

        File clazzFile = new File( file, this.prepareClassName(clazz) );
        if ( clazzFile.exists() ) {
            clazzFile.delete();
        }

        clazzFile.createNewFile();

        return clazzFile.getAbsolutePath();
    }

    @Override
    public IFork acquireClient(Class<?> clazz) throws ProcessException {
        return this.acquireClient(clazz, new String[] {} );
    }

    @Override
    public IFork acquireClient(Class<?> clazz, String[] args ) throws ProcessException {
        String clazzPath = clazz.getCanonicalName();
        String codeSource = clazz.getProtectionDomain().getCodeSource().getLocation().toExternalForm();

        List<String> classPath = new ArrayList<String>(this.classPath);
        if ( !classPath.contains(codeSource) ) {
            classPath.add( codeSource );
        }

        final IScriptExecutor executor = this.createForkExecutor(clazzPath, StringUtils.join(classPath, File.pathSeparator), args );
        if ( executor == null ) {
            throw new ProcessException("Failed to configure fork executor");
        }

        log.info("Fork executor configuration: " + executor.getExecCommand() );

        final ISystemProcess process;
        try {
            process = executor.spawn(this.service);
        } catch ( IOException e ) {
            throw new ProcessException( e.getMessage(), e );
        }

        IFork result;

        this.registry.add( result = new ForkImpl( this, this.getLoader(), process ) );

        IForkProtocolProcessor processor;
        this.processors.put( result, processor = this.createForkProcessor(result) );
        this.service.execute(processor);

        return result;
    }

    @Override
    public IForkProtocolProcessor getForkProcessor(IFork fork) {
        return this.processors.get(fork);
    }

    protected IForkProtocol createProtocol( IFork fork ) {
        return new StandardProtocol(fork.getInput(), fork.getOutput());
    }

    protected IForkProtocolProcessor createForkProcessor( IFork fork ) {
        return new StandardForkProtocolProcessor(
            this.protocolQueueCreator.createWorkQueue(),
            this.protocolQueueCreator.createResultsQueue(),
            this.createProtocol(fork)
        );
    }

    @Override
    public List<IFork> getClientsList() {
        return this.registry;
    }

    @Override
    public void shutdownAll() throws ProcessException {
        for ( IFork fork : this.getClientsList() ) {
            fork.shutdown();
        }

        this.getClientsList().clear();
        this.executors.clear();
        this.processors.clear();
    }

    public void resumeAll() throws ProcessException {
        for ( IFork fork : this.getClientsList() ) {
            if ( fork.isPaused() ) {
                fork.resume();
            }
        }
    }

    @Override
    public void pauseAll() throws ProcessException {
        for ( IFork fork : this.getClientsList() ) {
            if ( !fork.isPaused() ) {
                fork.pause();
            }
        }
    }
}
