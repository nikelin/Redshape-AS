package com.redshape.cmd;

import com.redshape.applications.Application;
import com.redshape.applications.ApplicationException;
import com.redshape.applications.bootstrap.Bootstrap;
import com.redshape.applications.bootstrap.IBootstrap;
import com.redshape.applications.bootstrap.IBootstrapAction;
import com.redshape.applications.bootstrap.actions.DatabaseInit;
import com.redshape.cmd.commands.HelpCommand;
import com.redshape.commands.CommandsFactory;
import com.redshape.commands.ExecutionException;
import com.redshape.commands.ICommand;
import com.redshape.utils.Registry;

import org.apache.log4j.*;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jul 21, 2010
 * Time: 5:04:52 PM
 * To change this template use File | Settings | File Templates.
 */
public final class Main extends com.redshape.applications.Application {
    private static final Logger log = Logger.getLogger( Main.class );
    private ICommand actualTask;

    public Main( String[] args, IBootstrap boot ) throws ApplicationException {
        super( Main.class, args, boot);

        this.processCommands();
    }

    @Override public void start() throws ApplicationException {
        if ( this.actualTask == null ) {
            this.actualTask = new HelpCommand();
        }

        if ( !this.actualTask.isValid() ) {
            log.error("Illegal arguments given");
            this.stop();
        }

        for ( IBootstrapAction action : this.actualTask.getBootstrapRequirements() ) {
            this.getBootstrap().addAction( action );
        }

        super.start();

        try {
            this.processTask(this.actualTask);
            Application.exit();
        } catch ( ExecutionException e ) {
            log.error("Task execution exception!", e );
        }
    }

    protected void processCommands() {
           try {
               CommandsFactory.addPackages( Registry.getConfig().get("settings").get("commands").list("package") );

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
           IBootstrap boot = new Bootstrap();
           boot.clear();

           try {
               Main main = new Main( args, boot );
               main.start();
           } catch ( Throwable e ) {
               log.error( "Excecution exception!", e );
           }
       }


}
