package com.redshape.commands;

import com.redshape.commands.annotations.Command;
import com.redshape.utils.InterfacesFilter;
import com.redshape.utils.PackageLoaderException;
import com.redshape.utils.PackagesLoader;

import org.apache.log4j.Logger;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 21, 2010
 * Time: 5:35:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class CommandsFactory {
    private final static Logger log = Logger.getLogger( CommandsFactory.class );
    private Map<Command, Class<? extends ICommand> > taskClasses = new HashMap<Command, Class<? extends ICommand>>();
    private static Set<String> packages = new HashSet<String>();
    private static CommandsFactory defaultInstance;
    private PackagesLoader packagesLoader;

    private CommandsFactory() {
        this.initialize();
    }

    public static void setDefault( CommandsFactory instance ) {
        defaultInstance = instance;
    }

    public static CommandsFactory getDefault() {
        if ( defaultInstance == null ) {
            defaultInstance = new CommandsFactory();
        }

        return defaultInstance;
    }
    
    public void setPackagesLoader( PackagesLoader loader ) {
    	this.packagesLoader = loader;
    }
    
    public PackagesLoader getPackagesLoader() {
    	return this.packagesLoader;
    }

    public Collection<Command> getTasks() {
        return this.taskClasses.keySet();
    }

    public ICommand createTask( String moduleId, String taskId ) throws InstantiationException {
        for ( Command commandAnnotation : this.taskClasses.keySet() ) {
            if ( commandAnnotation.module().equals( moduleId) && commandAnnotation.name().equals(taskId) ) {
                return this.createTask(commandAnnotation);
            }
        }

        return null;
    }

    public ICommand createTask( Command commandId) throws InstantiationException {
        try {
            return this.taskClasses.get(commandId).newInstance();
        } catch ( Throwable e ) {
            throw new InstantiationException();
        }
    }

    public static Set<String> getPackages() {
        return packages;
    }

    public static void addPackage( String path ) {
        packages.add(path);
    }

    public static void removePackage( String path ) {
        packages.remove( path );
    }

    public static void clearPackages() {
        packages.clear();
    }

    public static void addPackages( String[] adoption ) {
        addPackages( Arrays.asList(adoption) );
    }

    public static void addPackages( Collection<String> adoption ) {
        packages.addAll( adoption );
    }

    private void initialize() {
        try {
            for ( String path : getPackages() ) {
                this.processPackage( path );
            }
        } catch ( Throwable e ) {
            log.error( e.getMessage(), e );
        }
    }

    @SuppressWarnings("unchecked")
	private void processPackage( String path )  throws PackageLoaderException {
        Class<? extends ICommand>[] tasks = this.getPackagesLoader()
        										.<ICommand>getClasses(path, 
        												new InterfacesFilter<ICommand>( 
        													new Class[] { ICommand.class }, 
        													new Class[] { Command.class } 
        												) );
        for ( Class<? extends ICommand> task : tasks ) {
            this.taskClasses.put( task.getAnnotation(Command.class), task );
        }
    }

}
