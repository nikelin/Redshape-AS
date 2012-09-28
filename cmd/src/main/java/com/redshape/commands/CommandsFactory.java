package com.redshape.commands;

import com.redshape.commands.annotations.Command;

import java.util.Collection;
import java.util.HashSet;

/**
 * @package com.redshape.commands
 * @user cyril
 * @date 7/22/11 1:37 PM
 */
public class CommandsFactory implements ICommandsFactory {
    private Collection<ICommand> commands = new HashSet<ICommand>();

    public Collection<ICommand> getCommands() {
        return commands;
    }

    public void setCommands(Collection<ICommand> commands) {
        this.commands = commands;
    }

    @Override
    public Collection<ICommand> getTasks() {
        return this.commands;
    }

    @Override
    public ICommand createTask(String module, String task) throws InstantiationException {
        for ( ICommand taskInstance : this.getCommands() ) {
            Command command = taskInstance.getClass().getAnnotation( Command.class );
            if ( command == null ) {
                continue;
            }

            if ( command.name().equals( task) && command.module().equals(module) ) {
                return taskInstance;
            }
        }

        return null;
    }
}
