package com.redshape.persistence.migration;

import com.redshape.persistence.migration.components.DroppingTable;
import com.redshape.persistence.migration.components.Table;
import com.redshape.renderer.IRenderersFactory;
import com.redshape.utils.config.ConfigException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Feb 26, 2010
 * Time: 5:42:13 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractMigrator implements Migrator {
    private static final Logger log = Logger.getLogger( AbstractMigrator.class );
    private Connection connection;
    private IRenderersFactory renderersFactory;

    protected AbstractMigrator( IRenderersFactory renderersFactory ) throws MigrationException {
        log.info("Trying to create adapters to process migrations...");
        this.renderersFactory = renderersFactory;

        try {
            this.connection = this.createConnection();
        } catch ( Throwable e ) {
            throw new MigrationException( e.getMessage() );
        }
    }

    protected IRenderersFactory getRenderersFactory() {
        return this.renderersFactory;
    }

    public Connection getConnection() {
        return this.connection;
    }

    // @FIXME: due to DAO refactoring
    private Connection createConnection() throws SQLException, ConfigException {
        return null;
    }

    protected void create( Table table ) throws MigrationException, SQLException {
        try {
            String query = this.getRenderersFactory()
                                .forEntity(table)
                                .render(table)
                                .toString();

            log.info(query);

            this.getConnection().createStatement().executeUpdate( query );
        } catch ( Throwable e ) {
            log.error( e.getMessage(), e );
            throw new MigrationException();
        }
    }

    protected Set<String> getTablesList() throws MigrationException {
        return this.getTablesList( new String() );
    }

    protected Set<String> getTablesList( String source ) throws MigrationException {
        try {
            StringBuilder query = new StringBuilder();
            query.append("show tables");

            if ( !source.isEmpty() ) {
                query.append(" from " )
                     .append( source );
            }

            ResultSet rs = this.getConnection().createStatement().executeQuery( query.toString() );

            Set<String> result = new HashSet( rs.getFetchSize() );
            while( rs.next() ) {
                result.add( rs.getString( 1 ) );
            }

            return result;
        } catch ( SQLException e ) {
            throw new MigrationException();
        }
    }

    protected void remove( Table table ) throws MigrationException {
        try {
            String query = this.getRenderersFactory()
                                .forEntity( DroppingTable.class )
                                   .render( new DroppingTable(table) )
                                   .toString();

            this.getConnection().createStatement().executeUpdate( query );
        } catch ( Throwable e ) {
            throw new MigrationException();
        }
    }
    
}
