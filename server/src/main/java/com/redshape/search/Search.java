package com.redshape.search;

import com.redshape.search.engines.EngineException;
import com.redshape.search.engines.EnginesFactory;
import com.redshape.search.engines.ISearchEngine;
import com.redshape.search.engines.LuceneEngine;
import com.redshape.search.query.terms.*;
import com.redshape.search.serializers.ISerializer;
import com.redshape.search.serializers.SerializersFactory;

import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 30, 2010
 * Time: 3:42:52 PM
 * To change this template use File | Settings | File Templates.
 */
public final class Search {
    private static ISearchEngine currentEngine;

    public static ISearchEngine getLucene() throws InstantiationException {
        return EnginesFactory.getDefault().getEngine(LuceneEngine.class);
    }

    public static ISearchEngine getEngine() {
        return currentEngine;
    }

    public static void setEngine( ISearchEngine engine ) {
        currentEngine = engine;
    }

    public static ISerializer getSerializer( Class<? extends ISerializer> serializerClazz ) throws InstantiationException {
        return SerializersFactory.getDefault().getSerializer(serializerClazz);
    }

    public static Collection<ISearchable> search( ISearchTerm term, Class<? extends ISearchable> searchable ) throws EngineException {
        return getEngine().find( searchable, term );
    }

    public static AndTerm and( ISearchTerm left, ISearchTerm right ) {
        return new AndTerm( left, right );
    }

    public static OrTerm or( ISearchTerm left, ISearchTerm right ) {
        return new OrTerm( left, right );
    }

    public static NotTerm not( ISearchTerm operand ) {
        return new NotTerm( operand );
    }

    public static EqTerm eq( LiteralTerm operand, LiteralTerm value ) {
        return new EqTerm( operand, value );
    }

    public static LiteralTerm literal( String value ) {
        return new LiteralTerm( value );
    }
    
}
