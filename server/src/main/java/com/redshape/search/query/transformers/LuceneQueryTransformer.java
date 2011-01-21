package com.redshape.search.query.transformers;

import com.redshape.search.query.terms.*;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.util.Version;

import java.lang.reflect.Method;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 30, 2010
 * Time: 12:12:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class LuceneQueryTransformer implements IQueryTransformer<Query> {

    public Query transform( ISearchTerm term ) throws QueryTransformationException {
        try {
            return this.createQueryParser().parse( this._transform(term) );
        } catch ( Throwable e ) {
            throw new QueryTransformationException();
        }
    }

    protected String _transform( ISearchTerm term ) throws QueryTransformationException {
        try {
            for ( Method m : this.getClass().getMethods() ) {
                if ( m.getClass().isAssignableFrom( term.getClass() ) ) {
                    return (String) m.invoke( this, term );
                }
            }

            throw new QueryTransformationException();
        } catch ( QueryTransformationException e ) {
            throw e;
        } catch ( Throwable e ) {
            throw new QueryTransformationException();
        }
    }

    protected String _transform( IBinaryTerm term ) throws QueryTransformationException {
        StringBuilder builder = new StringBuilder();

        builder.append( this._transform( term.getLeft() ) );
        builder.append( this._transform( term.getOperation() ) );
        builder.append( this._transform( term.getRight() ) );

        return builder.toString();
    }

    protected String _transform( IUnaryTerm term ) throws QueryTransformationException {
        StringBuilder builder = new StringBuilder();

        builder.append( this._transform( term.getOperation() ) );
        builder.append( this._transform( term.getTerm() ) );

        return builder.toString();
    }

    protected String _transform( LiteralTerm term ) throws QueryTransformationException {
        return term.getValue();
    }

    protected String _transform( Operation operation ) throws QueryTransformationException {
        String result;
        switch ( operation ) {
            case AND:
                result = "AND";
            break;
            case OR:
                result = "OR";
            break;
            case NOT:
                result = "-";
            break;
            case EQUALS:
                result = "=";
            break;
            default:
                throw new QueryTransformationException("Unsupported operation");
        }

        return " ".concat( result.concat(" ") );
    }

    protected QueryParser createQueryParser() {
        return new QueryParser( Version.LUCENE_30, "", this.createAnalyzer() );
    }

    protected Analyzer createAnalyzer() {
        return new StandardAnalyzer( Version.LUCENE_30 );
    }

}
