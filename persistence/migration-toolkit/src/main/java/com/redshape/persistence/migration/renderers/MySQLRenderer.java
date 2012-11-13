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

package com.redshape.persistence.migration.renderers;

import com.redshape.renderer.IRenderer;
import com.redshape.renderer.IRenderersFactory;
import com.redshape.renderer.RendererException;

import java.util.Collection;
import java.util.regex.Pattern;

/**
 * WebCam Project
 *
 * @author nikelin
 * @project vio
 * @package com.vio.migration.renderers
 * @date Apr 6, 2010
 */
public abstract class MySQLRenderer<T> implements IRenderer<T, String> {
    private static final char ESCAPE_CHAR = '`';
    private final static Pattern NON_ESCAPABLE = Pattern.compile("[`() ,]");

    private IRenderersFactory renderersFactory;
    
    public MySQLRenderer( IRenderersFactory renderersFactory ) {
        super();
        
        this.renderersFactory = renderersFactory;
    }

    protected IRenderersFactory getRenderersFactory() {
        return this.renderersFactory;
    }
    
    protected String escapeField( String name ) {
        return isEscapingNeeds( name ) ? ESCAPE_CHAR + name + ESCAPE_CHAR : name;
    }

    @Override
    public void repaint(T renderable) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public String render( Collection<T> renderables ) throws RendererException {
        StringBuilder builder = new StringBuilder();

        int num = 0;
        for ( T renderable : renderables ) {
            builder.append( this.render(renderable) );

            if ( ( num = ++num ) < renderables.size() ) {
                builder.append( this.getCollectionSeparator() );
            }
        }

        return builder.toString();
    }

    protected String getCollectionSeparator() {
        return ",";
    }

    private static boolean isEscapingNeeds( String value ) {
        return !value.isEmpty()
                || (
                    !( NON_ESCAPABLE.matcher( String.valueOf( value.charAt(0) ) ).find()
                        && NON_ESCAPABLE.matcher(
                            String.valueOf( value.charAt( value.length() - 1 ) ) ).find() ) );
    }
}
