package com.redshape.search.collectors;

import com.redshape.search.ISearchable;
import com.redshape.search.annotations.Collector;
import com.redshape.utils.InterfacesFilter;
import com.redshape.utils.Registry;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 30, 2010
 * Time: 4:47:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class CollectorsFactory {
    private static final Logger log = Logger.getLogger( CollectorsFactory.class );

    private static CollectorsFactory defaultInstance = new CollectorsFactory();
    private static String collectorsPackage = "com.redshape.search.collectors";

    private Map< Class<? extends ISearchable>, IResultsCollector> collectors = new HashMap();

    public static CollectorsFactory getDefault() {
        return defaultInstance;
    }

    public static void setDefault( CollectorsFactory factory ) {
        defaultInstance = factory;
    }

    public CollectorsFactory() {
        this.initialize();
    }

    public static void setCollectorsPackage( String path ) {
        collectorsPackage = path;
    }

    public static String getCollectorsPackage() {
        return collectorsPackage;
    }

    public IResultsCollector getCollector( ISearchable searchable ) {
        return this.getCollector( searchable.getClass() );
    }

    public IResultsCollector getCollector( Class<? extends ISearchable> searchableClazz ) {
        IResultsCollector collector = this.collectors.get(searchableClazz);
        if ( collector != null ) {
            return collector;
        }

        return new StandardCollector();
    }

    private void initialize() {
        try {
            for( Class<? extends IResultsCollector> clazz : Registry.getPackagesLoader().<IResultsCollector>getClasses( collectorsPackage, new InterfacesFilter( new Class[] { IResultsCollector.class }, new Class[] {Collector.class } ) ) ) {
                Collector clazzMeta = clazz.getAnnotation( Collector.class );

                this.collectors.put( clazzMeta.entityType(), clazz.newInstance() );
            }
        } catch ( Throwable e ) {
            log.error( e.getMessage(), e );
        }
    }
}
