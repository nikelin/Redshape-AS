package com.redshape.ui.bindings.render;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JComponent;

import com.redshape.ui.UIException;
import com.redshape.ui.bindings.IViewModelBuilder;
import com.redshape.ui.bindings.properties.IPropertyUIBuilder;
import com.redshape.ui.bindings.render.components.ObjectUI;
import com.redshape.ui.bindings.views.ChoiceModel;
import com.redshape.ui.bindings.views.CollectionModel;
import com.redshape.ui.bindings.views.DefferedModel;
import com.redshape.ui.bindings.views.IChoiceModel;
import com.redshape.ui.bindings.views.ICollectionModel;
import com.redshape.ui.bindings.views.IComposedModel;
import com.redshape.ui.bindings.views.IDefferedModel;
import com.redshape.ui.bindings.views.IPropertyModel;
import com.redshape.ui.bindings.views.IViewModel;
import com.redshape.ui.bindings.views.PropertyModel;

public class ViewRenderer implements ISwingRenderer {
	private IViewModelBuilder modelsBuilder;
	private IPropertyUIBuilder uiBuilder;
	
	private List<DefferedItem> deffered = new ArrayList<DefferedItem>();
	private Map<Class<?>, ObjectUI> rendered = new HashMap<Class<?>, ObjectUI>();
	
	public ViewRenderer( IViewModelBuilder modelsBuilder, IPropertyUIBuilder uiBuilder ) {
		this.modelsBuilder = modelsBuilder;
		this.uiBuilder = uiBuilder;
	}
	
	protected IViewModelBuilder getModelsBuilder() {
		return this.modelsBuilder;
	}
	
	protected IPropertyUIBuilder getUIBuilder() {
		return this.uiBuilder;
	}
	
	@Override
	public ObjectUI render( JComponent parent, Class<?> type ) throws UIException {
		ObjectUI ui = this.process( parent, this.getModelsBuilder().createUI(type) );
		
		this.processDeffered(ui);
		
		return ui; 
	}
	
	protected void processDeffered( ObjectUI ui ) throws UIException {
		for ( DefferedItem item : this.deffered ) {
			IDefferedModel model = item.getModel();
			ObjectUI nestedUI = this.process( 
				(JComponent) item.getParent(), 
				this.getModelsBuilder().createUI( model.getDescriptor().getType(), model.getId(), model.getTitle(), false ),
				false
			);
			
			item.getParent().add( nestedUI.clone(), item.getPosition() );
		}
	}
	
	@SuppressWarnings("unchecked")
	protected void process( ObjectUI parent, IViewModel<?> model ) throws UIException {
		Class<? extends IViewModel<?>> modelClazz = ( Class<? extends IViewModel<?>> ) model.getClass();
		if ( modelClazz.equals( DefferedModel.class ) ) {
			this.deffered.add( new DefferedItem( parent, parent.getComponentCount(), (IDefferedModel) model ) );
		} else if ( modelClazz.equals( ChoiceModel.class ) ) {
			this.process( parent, (IChoiceModel) model );
		} else if ( modelClazz.equals( PropertyModel.class ) ) {
			this.process( parent, (IPropertyModel) model );
		} else if ( modelClazz.equals( CollectionModel.class ) ) {
			this.process( parent, (ICollectionModel) model );
		} else if ( model instanceof IComposedModel ) {
			this.process( ( JComponent) parent, (IComposedModel) model );
		} else {
			throw new UIException("Unsupported view type");
		}
	}
	
	protected void process( ObjectUI parent, ICollectionModel model ) throws UIException {
		// TODO: to be implemented
	}
	
	protected void process( ObjectUI parent, IDefferedModel model ) throws UIException {
		IComposedModel type = this.getModelsBuilder().createUI( model.getDescriptor().getType(), model.getId(), model.getTitle(), false );
		type.setId( model.getId() );
		
		this.process( ( JComponent) parent, type );
	}
	
	protected void process( ObjectUI parent, IPropertyModel model ) throws UIException {
		try {
			parent.addField( model, this.getUIBuilder().createRenderer( model.getDescriptor() ).renderEditor() );
		} catch ( InstantiationException e ) {
			throw new UIException( e.getMessage(), e );
		}
	}
	
	protected void process( ObjectUI parent, IChoiceModel model ) throws UIException {
		try {
			parent.addField( model, this.getUIBuilder().createListRenderer( this, model.getDescriptor() ).renderEditor() );
		} catch ( InstantiationException e ) {
			throw new UIException( e.getMessage(), e );
		}
	}

	protected ObjectUI process( JComponent parent, IComposedModel model ) throws UIException {
		return this.process( parent, model, true );
	}
	
	protected ObjectUI process( JComponent parent, IComposedModel model, boolean updateParent ) throws UIException {
		if ( this.rendered.containsKey( model.getDescriptor().getType() ) ) {
			return this.rendered.get( model.getDescriptor().getType() );
		}
		
		ObjectUI panel = new ObjectUI( model );
		
		for ( IViewModel<?> child : model.getChilds() ) {
			this.process(panel, child);
		}
		
		if ( updateParent ) {
			if ( ( parent instanceof ObjectUI ) ) {
				( (ObjectUI) parent ).addObject( panel );
			} else {
				parent.add(panel);
			}
		}

		this.rendered.put( model.getDescriptor().getType(), panel );
		
		return panel;
	}
	
	private static class DefferedItem {
		private ObjectUI parent;
		private int position;
		private IDefferedModel model;
		
		public DefferedItem( ObjectUI parent, int position, IDefferedModel model ) {
			this.parent = parent;
			this.position = position;
			this.model = model;
		}
		
		public int getPosition() {
			return this.position;
		}
		
		public IDefferedModel getModel() {
			return this.model;
		}
		
		public ObjectUI getParent() {
			return this.parent;
		}
	}
	
}
