package com.redshape.servlet.core.controllers.registry;

import com.redshape.servlet.core.controllers.IAction;

import java.util.Collection;

public interface IControllersRegistry {

    public IAction getInstance( String controller, String action ) throws InstantiationException;

	public void addAction(Class<? extends IAction> actionClazz);

	public Collection<Class<? extends IAction>> getActions();

    public String getViewPath( IAction action );

}