package com.redshape.utils.system.scripts;

import java.util.Collection;

/**
 * @author nikelin
 * @date 20:25
 */
public interface IScriptListExecutor<T> extends IScriptExecutor {

	public void addScript( T script );

	public Collection<T> getScripts();

	public void setListStyle( ScriptListStyle style );

}
