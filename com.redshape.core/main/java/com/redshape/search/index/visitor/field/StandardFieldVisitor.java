package com.redshape.search.index.visitor.field;

import com.redshape.search.ISearchable;
import com.redshape.search.annotations.AggregatedEntity;
import com.redshape.search.annotations.SearchableField;
import com.redshape.search.annotations.SearchableFieldSerializer;
import com.redshape.search.index.IIndex;
import com.redshape.search.index.IIndexField;
import com.redshape.search.index.IndexingType;
import com.redshape.search.index.builders.IndexBuilder;
import com.redshape.search.index.visitor.VisitorException;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Aug 13, 2010
 * Time: 2:52:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class StandardFieldVisitor implements IFieldVisitor {

    @Override
    public void visitField( IIndex index, Class<? extends ISearchable> entityClass, Field field ) throws VisitorException {
        if ( field.getAnnotation(AggregatedEntity.class) != null ) {
            this.visitAggregatedField( index, entityClass, field );
        } else {
            this.visitSimpleField( index, entityClass, field );
        }
    }

    protected void visitAggregatedField( IIndex index, Class<? extends ISearchable> entityClass, Field field ) throws VisitorException {
        try {
            AggregatedEntity annotation = field.getAnnotation( AggregatedEntity.class );

            switch ( annotation.type() ) {
                case COMPOSED:
                    this._aggregatedComposition( index, entityClass, field );
                break;
                case ID:
                    this._aggregatedById( index, entityClass, field );
                break;
            }
        } catch ( Throwable e ) {
            throw new VisitorException();
        }
    }

    private void _aggregatedComposition( IIndex index, Class<? extends ISearchable> entity, Field field ) throws VisitorException {
        try {
            AggregatedEntity annotation = field.getAnnotation( AggregatedEntity.class );

            Class<? extends ISearchable> aggregatedType;
            if ( annotation.targetEntity() != null ) {
                aggregatedType = annotation.targetEntity();
            } else {
                Class<?> fieldType = field.getType();
                if ( fieldType.isInterface() || Modifier.isAbstract( fieldType.getModifiers() ) ) {
                    throw new VisitorException("Aggregated field cannot be interface or must be specificated by targetEntity()");
                }

                aggregatedType = (Class<? extends ISearchable>) fieldType;
            }

            for ( IIndexField aggregatedField : IndexBuilder.newBuilder().getIndex( aggregatedType ).getFields() ) {
                if ( annotation.exclude().length > 0 &&
                        Arrays.binarySearch( annotation.exclude(), aggregatedField ) != -1 ){
                    continue;
                }

                if ( annotation.include().length > 0
                        && Arrays.binarySearch( annotation.include(), aggregatedField ) == -1 ) {
                    continue;
                }

                index.addField( aggregatedField );
            }
        } catch ( Throwable e ) {
            throw new VisitorException();
        }
    }

    private void _aggregatedById( IIndex index, Class<? extends ISearchable> entityClass, Field field ) throws VisitorException {
        IIndexField indexField = index.createField();

        String fieldName;
        SearchableField fieldAnnotation = field.getAnnotation( SearchableField.class );
        if ( fieldAnnotation == null ) {
            fieldName = field.getName();
        } else {
            fieldName = fieldAnnotation.name();
        }

        indexField.setType(IndexingType.NUMERICAL);
        indexField.setName( fieldName );
        indexField.markAnalyzable(false);
        indexField.markBinary(false);
        indexField.markStored(true);

        index.addField( indexField );
    }

    protected void visitSimpleField( IIndex index, Class<? extends ISearchable> entityClass, Field field ) {
        IIndexField indexPart = index.createField();

        SearchableField fieldMeta = field.getAnnotation( SearchableField.class );
        indexPart.setName( fieldMeta.name() );
        indexPart.setType( fieldMeta.type() );
        indexPart.setRank( fieldMeta.rank() );
        indexPart.markAnalyzable( fieldMeta.analyzable() );
        indexPart.markBinary( fieldMeta.binary() );
        indexPart.markStored( fieldMeta.stored() );

        SearchableFieldSerializer serializerMeta = field.getAnnotation( SearchableFieldSerializer.class );
        if ( serializerMeta != null ) {
            indexPart.setSerializer( serializerMeta.serializer() );
        }

        index.addField( indexPart );
    }

}
