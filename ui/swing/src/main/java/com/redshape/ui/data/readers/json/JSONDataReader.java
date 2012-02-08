package com.redshape.ui.data.readers.json;

import com.redshape.ui.data.IModelData;
import com.redshape.ui.data.IModelField;
import com.redshape.ui.data.IModelType;
import com.redshape.ui.data.readers.IDataReader;
import com.redshape.ui.data.readers.IListReader;
import com.redshape.ui.data.readers.ReaderException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 10.01.11
 * Time: 20:04
 * To change this template use File | Settings | File Templates.
 */
public class JSONDataReader<V extends IModelData> extends JSONReader<V> implements IListReader<V, String>, IDataReader<V, String> {
    private IModelType type;

    public IModelType getType() {
        return this.type;
    }

    public void setType( IModelType type ) {
        this.type = type;
    }

    @Override
    public Collection<V> processList( String data ) throws ReaderException {
        Object object = this.processObject( data );
        if ( !( object instanceof Collection) ) {
            throw new ReaderException("Object collections expected");
        }

        Collection<V> result = new HashSet<V>();
        for ( Object item : (Collection<?>) object ) {
            result.add( this._process(item) );
        }

        return result;
    }

    @Override
    public V process( String data ) throws ReaderException {
        Object object = super.processObject( data );
        if ( object instanceof Collection ) {
            throw new ReaderException("Single object data expected");
        }

        return this._process(object);
    }

    @SuppressWarnings("unchecked")
    protected V _process( Object object ) throws ReaderException {
        if ( !(object instanceof Map) ) {
            throw new ReaderException("Data must be in a map view");
        }

		final Map<Object, Object> dataObject = ( Map<Object, Object>) object;
        IModelData record = this.getType().createRecord();
        for ( IModelField field : this.getType().getFields() ) {
            if ( !dataObject.containsKey( field.getName() )
                        && field.isRequired() ) {
                throw new ReaderException("Field `" + field.getName() + "` is required.");
            }

            /** @TODO: fix with respect to format and type of record field value **/
            record.set( field.getName(), dataObject.get( field.getName() ) );
        }

        return (V) record;
    }

}
