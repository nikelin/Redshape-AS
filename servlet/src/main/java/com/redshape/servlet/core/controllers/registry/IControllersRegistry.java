package com.redshape.servlet.core.controllers.registry;

import java.util.Set;

import com.redshape.servlet.core.controllers.IAction;

public interface IControllersRegistry {

	public abstract void addAction(Class<? extends IAction> actionClazz);

	public abstract Set<Class<? extends IAction>> getActions();

	public abstract IAction createAction(String controller, String action)
			throws InstantiationException;

}