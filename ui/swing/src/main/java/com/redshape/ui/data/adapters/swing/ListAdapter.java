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

package com.redshape.ui.data.adapters.swing;

import com.redshape.ui.Dispatcher;
import com.redshape.ui.data.IModelData;
import com.redshape.ui.data.IStore;
import com.redshape.ui.application.events.UIEvents;

import javax.swing.*;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * @author nikelin
 * @date 26/04/11
 * @package com.redshape.ui.data.adapters.swing
 */
public class ListAdapter<T extends IModelData> extends JList {
	private static final long serialVersionUID = -6733654825153275566L;
	
	private IStore<T> store;

    public ListAdapter( IStore<T> store ) {
        super( new ListModelAdapter<T>(store) );

        this.store = store;
        this.init();
    }

    private void init() {
        if ( this.store == null ) {
            throw new IllegalArgumentException("null");
        }

        this.getModel().addListDataListener( new ListDataListener() {
            @Override
            public void intervalAdded(ListDataEvent e) {
                Dispatcher.get().forwardEvent( UIEvents.Core.Repaint, ListAdapter.this );
            }

            @Override
            public void intervalRemoved(ListDataEvent e) {
                Dispatcher.get().forwardEvent( UIEvents.Core.Repaint, ListAdapter.this );
            }

            @Override
            public void contentsChanged(ListDataEvent e) {
                Dispatcher.get().forwardEvent( UIEvents.Core.Repaint, ListAdapter.this );
            }
        });

        Dispatcher.get().forwardEvent( UIEvents.Core.Repaint, this );
    }

    public void setStore( IStore<T> store ) {
        this.store = store;
        this.init();
    }

}
