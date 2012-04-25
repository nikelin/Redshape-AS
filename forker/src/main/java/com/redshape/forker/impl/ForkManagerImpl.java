package com.redshape.forker.impl;

import com.redshape.forker.IFork;
import com.redshape.forker.IForkManager;
import com.redshape.forker.ProcessException;
import com.redshape.utils.IResourcesLoader;
import com.redshape.utils.StringUtils;
import com.redshape.utils.system.ISystemFacade;
import com.redshape.utils.system.processes.ISystemProcess;
import com.redshape.utils.system.scripts.IScriptExecutor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.forker.impl
 * @inspiredBy Jukka Zitting <jukkaz.wordpress.com>
 * @date 1/31/12 {2:07 PM}
 */
public class ForkManagerImpl implements IForkManager {

    private static final String MEMORY_LIMIT_PARAM = "-Xmx%sM";
    private static final String MEMORY_INITIAL_PARAM = "-Xms%sM";
    private static final String CLASSPATH_PARAM = "-cp %s";

    private IResourcesLoader loader;
    private ISystemFacade facade;

    private List<IFork> registry = new ArrayList<IFork>();
    private List<String> classPath = new ArrayList<String>();

    private String rootPath;
    private String jvmPath = "/usr/bin/java";
    private int memoryLimit;
    private int cpuLimit;

    public ForkManagerImpl(ISystemFacade facade, IResourcesLoader loader) {
        this.facade = facade;
        this.loader = loader;
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
        IScriptExecutor executor = this.createJVMExecutor()
                .addUnnamedParameter( String.format( CLASSPATH_PARAM, codeSource ) )
                .addUnnamedParameter( String.format( MEMORY_INITIAL_PARAM, this.getMemoryLimit() ) )
                .addUnnamedParameter( String.format( MEMORY_LIMIT_PARAM, this.getMemoryLimit() ) )
                .addUnnamedParameter( path )
                .addUnnamedParameter( StringUtils.join( args, " ") );

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

        IScriptExecutor executor = this.createForkExecutor(clazzPath, StringUtils.join(classPath, File.pathSeparator), args );
        if ( executor == null ) {
            throw new ProcessException("Failed to configure fork executor");
        }

        ISystemProcess process;
        try {
            process = executor.spawn();
        } catch ( IOException e ) {
            throw new ProcessException("Unable to spawn fork process");
        }

        return new ForkImpl( this.getLoader(), process );
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
