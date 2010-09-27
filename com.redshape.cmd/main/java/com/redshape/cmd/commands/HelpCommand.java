package com.redshape.cmd.commands;

import com.redshape.commands.AbstractCommand;
import com.redshape.commands.CommandsFactory;
import com.redshape.commands.ICommand;
import com.redshape.commands.annotations.Command;
import com.redshape.utils.StringUtils;

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

    @Override
    public boolean isSupports( String name ) {
        return true;
    }

    @Override
    public void process() {
        StringBuffer buffer = new StringBuffer();
        buffer.append( StringUtils.repeat("\n", 5 ) );
        buffer.append("VIO Runner")
              .append("\n");
        buffer.append("Available tasks:\n");
        buffer.append( StringUtils.repeat("-", 10) )
              .append("\n");

        Set<String> modules = new HashSet();

        Collection<Command> commands = CommandsFactory.getDefault().getTasks();
        for( Command command : commands) {
            modules.add( command.module() );
        }

        for ( String module : modules ) {
            buffer.append("Module ")
                  .append( module )
                  .append( ":" )
                  .append( "\n" );
            
            for ( Command command : commands ) {
                if ( !command.module().equals( module ) ) {
                    continue;    
                }

                buffer.append( command.name() )
                      .append( ": ")
                      .append( command.helpMessage() );

                try {
                    ICommand commandInstance = CommandsFactory.getDefault().createTask( command.module(), command.name() );
                    for ( String property : commandInstance.getSupported() ) {
                        buffer.append( "-" )
                              .append( property )
                              .append( " " );
                    }

                    buffer.append( "\n" );
                } catch ( InstantiationException e ) {
                    buffer.append( " [ERROR] unavailable command!" );
                }
            }

            buffer.append("-------------")
                  .append("To run target task enter its' path in the next way:\n")
                  .append(" [module-name] [task-name] -[arg1]=[arg1-value]...")
                  .append("\n\n\n");
        }

        buffer.append("Total commands: ")
              .append( commands.size() );

        System.out.println( buffer.toString() );
    }
    
    public String[] getSupported() {
        return new String[] {};
    }
}
