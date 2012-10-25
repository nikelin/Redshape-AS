package com.redshape.commands;

import com.redshape.commands.annotations.Command;
import com.redshape.utils.Commons;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.Collection;
import java.util.HashSet;

/**
 * @package com.redshape.commands
 * @user cyril
 * @date 7/22/11 1:37 PM
 */
public class CommandsFactory implements ICommandsFactory {
    private Collection<ICommand> commands = new HashSet<ICommand>();
    private Writer writer;

    public CommandsFactory(Collection<ICommand> commands) {
        this(commands, new PrintWriter(System.out) );
    }

    public CommandsFactory(Collection<ICommand> commands, Writer writer ) {
        Commons.checkNotNull(commands);

        this.commands = commands;
        this.writer = writer;
    }

    @Override
    public void setWriter(Writer writer) {
        this.writer = writer;
    }

    @Override
    public Collection<ICommand> getTasks() {
        return this.commands;
    }

    @Override
    public ICommand createTask(String module, String task) throws InstantiationException {
        for ( ICommand taskInstance : this.commands ) {
            Command command = taskInstance.getClass().getAnnotation( Command.class );
            if ( command == null ) {
                continue;
            }

            if ( command.name().equals( task) && command.module().equals(module) ) {
                taskInstance.setWriter( this.writer );
                return taskInstance;
            }
        }

        return null;
    }
}
