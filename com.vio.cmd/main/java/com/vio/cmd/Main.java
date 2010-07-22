package com.vio.cmd;

import com.vio.applications.ApplicationException;
import com.vio.applications.bootstrap.Bootstrap;
import com.vio.applications.bootstrap.actions.DatabaseInit;
import com.vio.cmd.commands.HelpCommand;
import com.vio.commands.CommandsFactory;
import com.vio.commands.ExecutionException;
import com.vio.commands.ICommand;
import com.vio.utils.Registry;

import org.apache.log4j.*;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jul 21, 2010
 * Time: 5:04:52 PM
 * To change this template use File | Settings | File Templates.
 */
public final class Main extends com.vio.applications.Application {
    private static final Logger log = Logger.getLogger( Main.class );
    private ICommand actualTask;

    public Main( String[] args, Bootstrap boot ) throws ApplicationException {
        super( Main.class, args, boot);

        this.processCommands();
    }

    @Override public void start() throws ApplicationException {
        if ( this.actualTask == null ) {
            this.actualTask = new HelpCommand();
        }

        super.start();

        try {
            this.processTask(this.actualTask);
        } catch ( ExecutionException e ) {
            log.error("Task execution exception!", e );
        }
    }

    protected void processCommands() {
           try {
               CommandsFactory.addPackages( Registry.getApiServerConfig().getCommandsPackages() );

               String module = null;
               ICommand task = null;
               for ( String arg : this.getEnvArgs() ) {
                   if ( !arg.startsWith("-") ) {
                       if ( module != null) {
                           if ( task != null ) {
                               this.processTask(task);
                               task = null;
                           }

                           task = CommandsFactory.getDefault().createTask( module, arg );
                       } else {
                           module = arg;
                       }
                   } else if ( arg.startsWith("-") && task != null  ) {
                       String[] propertyParts = arg.split("=");
                       if ( propertyParts.length < 2 ) {
                           continue;
                       }

                       task.setProperty(
                           propertyParts[0].substring(1),
                           propertyParts[1]
                       );
                   }
               }

               if ( task != null ) {
                   this.actualTask = task;
               } else if ( module != null ) {
                   task = CommandsFactory.getDefault().createTask( null, module );
                   if ( task != null ) {
                       this.actualTask = task;
                   }
               }
           } catch ( InstantiationException e ) {
               System.out.println("Requested task does not supports! Write `help` for advice.");
           } catch ( IllegalArgumentException e ) {
               System.out.println("Insufficiently or illegal arguments given!");
           } catch ( ExecutionException e )  {
               System.out.println( "Command processing exception");
               e.printStackTrace();
           } catch ( Throwable e ) {
               System.out.println("Something goes wrong...");
               e.printStackTrace();
           }
       }

       private void processTask( ICommand task ) throws ExecutionException {
           if ( !task.isValid() ) {
               throw new IllegalArgumentException();
           }

           task.process();
       }

       public static void main( String[] args ) {
           Bootstrap boot = new Bootstrap();
           boot.clearActionPackages();
           boot.clearActions();

           boot.addAction( new DatabaseInit() );

           try {
               Main main = new Main( args, boot );
               main.start();
           } catch ( Throwable e ) {
               log.error( "Excecution exception!", e );
           }
       }


}
