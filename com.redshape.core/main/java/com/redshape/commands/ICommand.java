package com.redshape.commands;

import com.redshape.applications.bootstrap.IBootstrapAction;

import java.util.Collection;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 21, 2010
 * Time: 5:29:06 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ICommand {

    public void process() throws ExecutionException;

    public void setProperty( String name, String value );

    public Map<String, String> getProperties();

    public boolean isSupports( String name );
    
    public String[] getSupported();

    public String[] getImportant();

    public boolean isValid();

    public Collection<? extends IBootstrapAction> getBootstrapRequirements();

}
