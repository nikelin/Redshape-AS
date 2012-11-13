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

package com.redshape.ui.panels.objects;

import com.redshape.ui.application.events.AppEvent;
import com.redshape.ui.application.events.IEventHandler;
import com.redshape.ui.application.events.UIEvents;
import com.redshape.ui.components.InteractionAction;
import com.redshape.ui.utils.UIConstants;
import com.redshape.ui.utils.UIRegistry;

import javax.swing.*;
import java.awt.*;

public class FileField extends JComponent {
	private static final long serialVersionUID = 7552188776619747507L;
	
	public static class Events extends UIEvents {
		protected Events( String code ) {
			super( code );
		}
		
		public static final Events Selected = new Events("FileField.Events.Selected");
	}
	
	private JLabel labelElement;
	private JTextField pathElement;
	private IEventHandler handler;
	
	public FileField() {
		this( null );
	}
	
	public FileField( String label ) {
		this(label, null);
	}
	
	public FileField( String label, String initialPath ) {
		this(label, initialPath, null);
	}
	
	public FileField( String label, String initialPath, IEventHandler handler ) {
		super();
		
		this.handler = handler;
		
		this.buildUI();
		
		this.setLabel( label );
		this.setPath(initialPath);
	}
	
	protected void buildUI() {
		this.setLayout( new BoxLayout( this, BoxLayout.LINE_AXIS ) );
		
		Box box = Box.createHorizontalBox();
		box.setMinimumSize( new Dimension( 400, 25 ) );
		box.add( this.labelElement = new JLabel() );
		
		this.pathElement = new JTextField();
		this.pathElement.setMaximumSize( new Dimension( 100, 25 ) );
		this.pathElement.setEditable(false);
		box.add( this.pathElement );
		
		box.add( new JButton(
			new InteractionAction(
				"...",
				new IEventHandler() {
					private static final long serialVersionUID = -2579366352786656538L;

					@Override
					public void handle(AppEvent event) {
						FileField.this.showChooserDialog();
					}
				}
			)
		) );
		this.add(box);
	}
	
	protected void showChooserDialog() {
		FileDialog dialog = new FileDialog( UIRegistry.<JFrame>get(UIConstants.System.WINDOW), "Select file", FileDialog.LOAD );
		dialog.setVisible(true);
		
		if ( this.pathElement.getText() != null && !this.pathElement.getText().isEmpty() ) {
			dialog.setDirectory( this.pathElement.getText() );
		}
		
		if ( FileField.this.handler != null ) {
			FileField.this.handler.handle(
				new AppEvent(
					FileField.Events.Selected,
					dialog.getFile()
				)
			);
		}
		
		FileField.this.setPath( dialog.getDirectory() + "/" + dialog.getFile() );
		FileField.this.revalidate();
		FileField.this.repaint();
	}
	
	public void setPath( String path ) {
		this.pathElement.setText( path );
	}
	
	public void setLabel( String label ) {
		this.labelElement.setText(label);
	}
	
}
