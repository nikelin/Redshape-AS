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

package com.redshape.utils.serializing.beans;

import com.redshape.utils.serializing.ObjectsFlusher;
import com.redshape.utils.serializing.ObjectsLoaderException;

import java.io.OutputStream;

/**
 * XStream based marshalling manager
 *
 * @author nikelin
 * @date 07/04/11
 * @package com.redshape.utils.serializing
 */
public class BeansFlusher extends AbstractBeansSerializer implements ObjectsFlusher{

    @Override
    public void flush(Object object, OutputStream target) throws ObjectsLoaderException {
       this.getLoader().toXML( object, target );
    }

}
