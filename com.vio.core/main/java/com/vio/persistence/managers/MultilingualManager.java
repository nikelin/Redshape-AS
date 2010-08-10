package com.vio.persistence.managers;

import com.vio.persistence.entities.IMultilingual;
import com.vio.persistence.entities.Locale;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 24, 2010
 * Time: 4:54:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class MultilingualManager extends Manager {

    public MultilingualManager() {
        super( IMultilingual.class );
    }

    public <T extends IMultilingual> boolean hasTranslation( T entity, Locale locale ) throws ManagerException {
        CriteriaBuilder builder = this.getManager().getCriteriaBuilder();

        CriteriaQuery<Long> query = builder.createQuery(Long.class);

        Root<T> from = query.from( (Class<T>) entity.getClass() );
        query.select( builder.count(from) );
        query.where( builder.and(
            builder.equal(
                from.<Long>get("locale.id"),
                builder.parameter( Long.class, "locale" )
            ),
            builder.equal(
                from.<Long>get("original.id"),
                builder.parameter( Long.class, "original" )
            ),
            builder.notEqual(
                from.<Long>get("id"),
                builder.parameter( Long.class, "id" )
            )
        ) );

        TypedQuery<Long> typedQuery = this.getManager().createQuery(query);
        typedQuery.setParameter("locale", locale.getId() )
                  .setParameter("original", entity.getOriginal().getId() )
                  .setParameter("id", entity.getId() );

        if ( typedQuery.getSingleResult() != 0 ) {
            return true;
        }

        return false;
    }

    public <T extends IMultilingual> T getTranslation( T entity, Locale locale ) throws ManagerException {
        CriteriaBuilder builder = this.getManager().getCriteriaBuilder();

        CriteriaQuery<T> query = builder.createQuery( (Class<T>) entity.getClass() );

        Root<T> from = query.from( (Class<T>) entity.getClass() );
        query.where( builder.and(
            builder.equal(
                from.<Long>get("locale.id"),
                builder.parameter( Long.class, "locale" )
            ),
            builder.equal(
                from.<Long>get("original.id"),
                builder.parameter( Long.class, "original" )
            ),
            builder.notEqual(
                from.<Long>get("id"),
                builder.parameter( Long.class, "id" )
            )
        ) );

        return this.getManager()
                   .createQuery( query )
                   .setParameter( "locale", locale.getId() )
                   .setParameter( "original", entity.getOriginal().getId() )
                   .setParameter( "id", entity.getId() )
                   .getSingleResult();
    }

}
