package com.redshape.persistence.dao.query.executors.result;

import com.redshape.persistence.entities.IDtoCapable;
import com.redshape.persistence.entities.IEntity;
import com.redshape.utils.Commons;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.persistence.jms.result
 * @date 2/6/12 {2:47 PM}
 */
public class DTOResult<T extends IEntity> extends ExecutorResult<T> {

    public DTOResult(Object object) {
        super( prepareInput(object) );
    }
    
    protected static Object prepareInput( Object object ) {
        List result = new ArrayList();
        if (object == null) {
            result = Commons.emptyList();
        } else if (object instanceof Collection) {
            for ( Object obj : (Collection) object) {
                if ( !( obj instanceof IDtoCapable) ) {
                    continue;
                }
                
                result.add( ((IDtoCapable) obj).toDTO() );
            }
        } else if ( object instanceof IDtoCapable ) {
            result = Commons.list(((IDtoCapable) object).toDTO());
        } else {
            result = Commons.list(object);
        }

        return result;
    }
}
