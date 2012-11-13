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

package com.redshape.renderer;

import java.util.Map;

/**
 * @author nikelin
 * @package com.redshape.renderer
 */
public interface IRenderersFactory {

	/**
     * String which represent factory ID in configuration
     *
     * @return Strng
     */
    public String getFactoryId();

    /**
     * Bind renderer entity to specified renderable entity
     * @param entityClazz
     * @param renderer
     */
    public <T, V> void addRenderer( Class<T> entityClazz, Class<? extends IRenderer<T, V>> renderer );

    public <T, V> void addRenderers( Map<Class<T>, Class<? extends IRenderer<T, V>>> renderers );
    
    /**
     *
     * @param object
     * @param <T>
     * @param <V>
     * @return
     * @throws RendererException
     */
    public <T, V> IRenderer<T, V> forEntity( Class<T> object );

    public <T, V> IRenderer<T, V> forEntity( T entity );

    public <T, V> V render( T entity );
}
