package com.vio.search.engines;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 30, 2010
 * Time: 3:41:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class EnginesFactory {
    private static EnginesFactory defaultInstance = new EnginesFactory();
    private static Map<Class<? extends ISearchEngine>, ISearchEngine> engines = new HashMap();

    public static void setDefault( EnginesFactory factory ) {
        defaultInstance = factory;
    }

    public static EnginesFactory getDefault() {
        return defaultInstance;
    }

    public ISearchEngine getEngine( Class<? extends ISearchEngine> clazz ) throws InstantiationException {
        ISearchEngine engineInstance = this.engines.get( clazz );
        if ( engineInstance != null ) {
            return engineInstance;
        }

        try {
            engineInstance = clazz.newInstance();
        } catch ( Throwable e ) {
            throw new InstantiationException();
        }

        this.engines.put( clazz, engineInstance );

        return engineInstance;
    }

}
