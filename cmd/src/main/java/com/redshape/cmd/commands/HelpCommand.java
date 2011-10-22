package com.redshape.cmd.commands;

import com.redshape.cmd.Main;
import com.redshape.commands.AbstractCommand;
import com.redshape.commands.ICommand;
import com.redshape.commands.ICommandsFactory;
import com.redshape.commands.annotations.Command;
import com.redshape.utils.StringUtils;
import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 22, 2010
 * Time: 12:15:09 PM
 * To change this template use File | Settings | File Templates.
 */
@Command( module = "migrate", name = "help", helpMessage = "description of available tasks" )
public class HelpCommand extends AbstractCommand {
	private static final Logger log = Logger.getLogger(HelpCommand.class);

    @Override
    public boolean isSupports( String name ) {
        return true;
    }

    protected ICommandsFactory getFactory() {
        return Main.getContext().getBean(ICommandsFactory.class);
    }

    @Override
    public void process() {
        StringBuffer buffer = new StringBuffer();
        buffer.append( StringUtils.repeat("\n", 5 ) );
        buffer.append("Redshape Commands Runner")
              .append("\n");
        buffer.append("Available tasks:\n");
        buffer.append( StringUtils.repeat("-", 10) )
              .append("\n");

        Set<String> modules = new HashSet();

        Collection<ICommand> commands = this.getFactory().getTasks();
        for( ICommand command : commands) {
            Command commandMeta = command.getClass().getAnnotation( Command.class );
            if ( commandMeta == null ) {
                continue;
            }

            modules.add( commandMeta.module() );
        }

        for ( String module : modules ) {
            buffer.append("Module `")
                  .append( module )
                  .append( "`:" )
                  .append( "\n" );
            
            for ( ICommand commandInstance : commands ) {
                Command command = commandInstance.getClass().getAnnotation( Command.class );
                if ( !command.module().equals( module ) ) {
                    continue;    
                }

                buffer.append("`")
                      .append( command.name() )
                      .append("`")
                      .append( " - ")
                      .append( command.helpMessage() );

                buffer.append(" ( Supported properties: ");

                int i = 0;
                for ( String property : commandInstance.getSupported() ) {
                    buffer.append( "-" )
                          .append( property );

                    if ( i++ != ( commandInstance.getSupported().length - 1 ) ) {
                          buffer.append( "," );
                    }
                }
                buffer.append(" )");

                buffer.append( "\n" );
            }

            buffer.append("\n-------------\n")
                  .append("To run target task enter its' path in the next form:\n")
                  .append(" [module-name] [task-name] -[arg1]=[arg1-value]...")
                  .append("\n\n\n");
        }

        buffer.append("Total commands: ")
              .append( commands.size() );

        log.info( buffer.toString() );
    }
    
    public String[] getSupported() {
        return new String[] {};
    }
}
