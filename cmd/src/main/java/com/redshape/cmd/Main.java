package com.redshape.cmd;

import com.redshape.applications.ApplicationException;
import com.redshape.applications.SpringApplication;
import com.redshape.applications.bootstrap.LoggingStarter;
import com.redshape.commands.ExecutionException;
import com.redshape.commands.ICommand;
import com.redshape.commands.ICommandsFactory;
import com.redshape.commands.annotations.Command;
import com.redshape.utils.Commons;
import com.redshape.utils.validators.ValidationException;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author nikelin
 * @package com.redshape.cmd
 */
public final class Main extends SpringApplication {
    static {
        LoggingStarter.init();
    }

    private static final Logger log = Logger.getLogger( Main.class );
    private ICommand actualTask;

    public Main( String[] args ) throws ApplicationException {
        super(args);
    }

    protected ICommandsFactory getCommandsFactory() {
        return getContext().getBean( ICommandsFactory.class );
    }

    @Override
    public void start() throws ApplicationException  {
        super.start();

        this.processCommands();

        System.exit(0);
    }

    protected void processCommands() {
        try {
            String module = null;
            boolean first = true;
            Collection<ICommand> processQueue = new ArrayList<ICommand>();
            Map<String, Object> properties = new HashMap<String, Object>();
            for ( String arg : this.getEnvArgs() ) {
                if ( first && arg.endsWith(".jar") ) {
                    first = false;
                    continue;
                }

                if ( arg.contains(":") && !arg.startsWith("-") ) {
                    String[] commandParts = arg.split(":");
                    Commons.checkArgument( commandParts.length == 2 );
                    Commons.checkArgument( !commandParts[0].isEmpty() );

                    ICommand task;
                    if ( commandParts[1].isEmpty() ) {
                        task = this.getCommandsFactory().createTask(null, module);
                    } else {
                        task = this.getCommandsFactory().createTask(commandParts[0], commandParts[1]);
                    }

                    if ( task != null ) {
                        processQueue.add(task);
                    }
                } else if ( arg.startsWith("-")  ) {
                    String[] propertyParts = arg.split("=");
                    if ( propertyParts.length < 2 ) {
                        continue;
                    }

                    properties.put(
                        propertyParts[0].substring(1),
                        propertyParts[1]
                    );
                }
            }

            for ( String key : properties.keySet() ) {
                String[] parts = key.split(":");
                if ( parts.length == 1 ) {
                    applyProperty( processQueue, parts[0], String.valueOf( properties.get(key) ) );
                } else {
                    if ( parts.length < 3 ) {
                        throw new IllegalArgumentException("Illegal argument format: " + key );
                    }
                    applyProperty( processQueue, parts[0], parts[1], parts[2], String.valueOf( properties.get(key) ) );
                }
            }

            for ( ICommand command : processQueue ) {
                if ( command.isValid() ) {
                    command.process();
                } else {
                    throw new ValidationException("Command validation failed");
                }
            }
        } catch ( InstantiationException e ) {
            log.error("Requested task does not supports! Write `help` for advice.", e );
            System.exit(3);
        } catch ( IllegalArgumentException e ) {
            log.error("Insufficiently or illegal arguments given!", e);
            System.exit(4);
        } catch ( ExecutionException e )  {
            log.error( "Command processing exception", e);
            System.exit(2);
        } catch ( Throwable e ) {
            log.error("Something goes wrong...", e);
            System.exit(1);
        }
    }

    private void applyProperty( Collection<ICommand> processQueue, String name, String value ) {
        for ( ICommand command : processQueue ) {
            if ( command.isSupports(name) ) {
                command.setProperty( name, value );
            }
        }
    }

    private void applyProperty( Collection<ICommand> processQueue, String module,
                                String task, String name, String value ) {
        for ( ICommand command : processQueue ) {
            Command commandMeta = command.getClass().getAnnotation( Command.class );
            if ( commandMeta == null ) {
                continue;
            }

            if ( !( commandMeta.module().equals(module) && commandMeta.name().equals(task) ) ) {
                continue;
            }

            if ( !command.isSupports(name) ) {
                throw new IllegalArgumentException("Requested command " + module + ":" + task +
                        " is not supported property " + name + "!");
            }

            command.setProperty(name, value);
        }
    }

    private void processTask( ICommand task ) throws ExecutionException {
        if ( !task.isValid() ) {
            throw new IllegalArgumentException();
        }

        task.process();
    }

    public static void main( String[] args ) {
        try {
            Main main = new Main( args );
            main.start();
        } catch ( Throwable e ) {
            log.error( "Excecution exception!", e );
        }
    }


}
