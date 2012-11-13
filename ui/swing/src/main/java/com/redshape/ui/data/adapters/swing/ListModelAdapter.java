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

import com.redshape.ui.application.UnhandledUIException;
import com.redshape.ui.data.IModelData;
import com.redshape.ui.data.IStore;
import com.redshape.ui.data.loaders.LoaderException;
import com.redshape.ui.data.stores.StoreEvents;
import com.redshape.ui.application.events.AppEvent;
import com.redshape.ui.application.events.IEventHandler;

import javax.swing.*;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import java.util.Collection;
import java.util.HashSet;

/**
 * @author nikelin
 * @date 26/04/11
 * @package com.redshape.ui.data.adapters.swing
 */
public class ListModelAdapter<T extends IModelData> extends DefaultListModel {
	private static final long serialVersionUID = -1496426415629688805L;
	
	private IStore<T> store;
    private Collection<ListDataListener> listeners = new HashSet<ListDataListener>();

    public ListModelAdapter( IStore<T> store ) {
        this.store = store;

        if ( this.store != null ) {
            this.init();
        }
    }

    protected void onRecordAdded( T record, int index ) {
        for ( ListDataListener listener : this.listeners ) {
            listener.intervalAdded(
                new ListDataEvent( record, ListDataEvent.INTERVAL_ADDED,
                       index, index ) );
        }

        this.addElement( record );
    }

    protected void onRecordRemoved( T record, int index ) {
        for ( ListDataListener listener : this.listeners ) {
            listener.intervalAdded(
                new ListDataEvent( record, ListDataEvent.INTERVAL_REMOVED,
                        index, index ) );
        }

        this.removeElement( record );
    }

    protected void onRecordChanged( T record, int index ) {
        for ( ListDataListener listener : this.listeners ) {
            listener.contentsChanged(
                new ListDataEvent( record, ListDataEvent.CONTENTS_CHANGED,
                        index, index ) );
        }
    }

    public void init() {
        try {
            this.store.load();
        } catch ( LoaderException e ) {
            throw new UnhandledUIException( e.getMessage(), e );
        }

        if ( this.store.count() != 0 ) {
            int i = 0;
            for ( T record : this.store.getList() ) {
                this.onRecordAdded( record, i++ );
            }
        }

        this.store.addListener( StoreEvents.Changed, new IEventHandler() {
			private static final long serialVersionUID = -6079645314129172752L;

			@Override
            public void handle(AppEvent event) {
                ListModelAdapter.this.onRecordChanged( event.<T>getArg(0),
                                                       event.<Integer>getArg(1) );
            }
        });

        this.store.addListener( StoreEvents.Added, new IEventHandler() {
			private static final long serialVersionUID = 4593398769371945808L;

			@Override
            public void handle(AppEvent event) {
                ListModelAdapter.this.onRecordAdded( event.<T>getArg(0),
                                                     event.<Integer>getArg(1) );
            }
        });

        this.store.addListener( StoreEvents.Removed, new IEventHandler() {
			private static final long serialVersionUID = 3911306137829179844L;

			@Override
            public void handle(AppEvent event) {
                ListModelAdapter.this.onRecordRemoved( event.<T>getArg(0),
                                                       event.<Integer>getArg(1) );
            }
        });
    }

    @Override
    public int getSize() {
        return this.store.count();
    }

    @Override
    public Object getElementAt(int i) {
        return this.store.getAt(i);
    }

    @Override
    public void addListDataListener(final ListDataListener listDataListener) {
        this.listeners.add(listDataListener);
    }

    @Override
    public void removeListDataListener(ListDataListener listDataListener) {
        this.listeners.remove( listDataListener );
    }
}
