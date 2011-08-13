package com.redshape.commands;

import java.util.Collection;

/**
 * @package com.redshape.commands
 * @user cyril
 * @date 7/22/11 1:35 PM
 */
public interface ICommandsFactory {

    public Collection<ICommand> getTasks();

    public ICommand createTask( String module, String task ) throws InstantiationException;

}
