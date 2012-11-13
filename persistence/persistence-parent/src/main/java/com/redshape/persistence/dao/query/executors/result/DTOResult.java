/*
 * Copyright 2012 Cyril A. Karpenko
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

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
