package com.redshape.servlet.core.controllers.registry;

import com.redshape.servlet.core.controllers.IAction;

import java.util.Set;

public interface IControllersRegistry {

    public IAction getInstance( String controller, String action ) throws InstantiationException;

	public void addAction(Class<? extends IAction> actionClazz);

	public Set<Class<? extends IAction>> getActions();

    public String getViewPath( IAction action );

}