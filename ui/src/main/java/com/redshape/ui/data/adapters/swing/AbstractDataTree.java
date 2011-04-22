package com.redshape.ui.data.adapters.swing;

import com.redshape.ui.data.IModelData;
import com.redshape.ui.data.IStore;
import com.redshape.ui.data.stores.StoreEvents;
import com.redshape.ui.events.AppEvent;
import com.redshape.ui.events.IEventHandler;
import com.redshape.ui.tree.AbstractTree;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Adapter to binds tree withing specified store
 *
 * @author nikelin
 * @date 12/04/11
 * @package com.redshape.ui.tree
 */
public abstract class AbstractDataTree<V extends IModelData, T extends IStore<V>> extends AbstractTree {
	private T store;

	public AbstractDataTree( T store ) {
		this( store, true );
	}

	public AbstractDataTree( T store, boolean initNodes ) {
		super();

		this.store = store;

		this.bindStore();

		if ( initNodes ) {
			this.initNodes();
		}
	}

	protected void initNodes() {
		for ( V record : this.getStore().getList() ) {
			this.addRecord(record);
		}
	}

	protected T getStore() {
		return this.store;
	}

	protected void bindStore() {
		this.getStore().addListener(
			StoreEvents.Refresh,
			new IEventHandler() {
				@Override
				public void handle(AppEvent event) {
					AbstractDataTree.this.removeNodes();
					AbstractDataTree.this.initNodes();
				}
			}
		);

		this.getStore().addListener(
			StoreEvents.Changed,
			new IEventHandler() {
				@Override
				public void handle(AppEvent event) {
					AbstractDataTree.this.onRecordChanged(event);
				}
			}
		);

		this.getStore().addListener(
			StoreEvents.Removed,
			new IEventHandler() {
				@Override
				public void handle(AppEvent event) {
					AbstractDataTree.this.onRecordRemove(event);
				}
			}
		);

		this.getStore().addListener(
			StoreEvents.Added,
			new IEventHandler() {
				@Override
				public void handle(AppEvent event) {
					AbstractDataTree.this.onRecordAdd(event);
				}
			}
		);
	}

	public V getSelectedRecord() {
		return (V) ( (DefaultMutableTreeNode) this.getSelectionModel()
												  .getSelectionPath()
												  .getLastPathComponent() )
						.getUserObject();
	}

	abstract public void addRecord( V record );

	abstract public void removeRecord( V record );

	abstract protected void invalidateRecord( V record );

	protected void onRecordAdd( AppEvent event ) {
		this.addRecord( event.<V>getArg(0) );
	}

	protected void onRecordRemove( AppEvent event ) {
		this.addRecord( event.<V>getArg(0) );
	}

	protected void onRecordChanged( AppEvent event ) {
		this.invalidateRecord( event.<V>getArg(0) );
	}

}
