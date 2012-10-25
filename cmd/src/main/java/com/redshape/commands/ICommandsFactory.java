package com.redshape.commands;

import java.io.Writer;
import java.util.Collection;

/**
 * @package com.redshape.commands
 * @user cyril
 * @date 7/22/11 1:35 PM
 */
public interface ICommandsFactory {

    public void setWriter( Writer writer );

    public Collection<ICommand> getTasks();

    public ICommand createTask( String module, String task ) throws InstantiationException;

}
