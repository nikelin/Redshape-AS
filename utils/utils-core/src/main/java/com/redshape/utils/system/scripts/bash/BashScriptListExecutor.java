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

package com.redshape.utils.system.scripts.bash;

import com.redshape.utils.system.scripts.IScriptExecutor;
import com.redshape.utils.system.scripts.IScriptListExecutor;
import com.redshape.utils.system.scripts.ScriptListStyle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BashScriptListExecutor extends BashScriptExecutor
									implements IScriptListExecutor<BashScriptExecutor> {
	private List<BashScriptExecutor> scripts = new ArrayList<BashScriptExecutor>();
	private ScriptListStyle style;

	public BashScriptListExecutor() {
		this.style = ScriptListStyle.EXCLUSIVE;
	}

	@Override
	public String getExecCommand() {
		StringBuilder builder = new StringBuilder();

		int counter = 0;
		for ( BashScriptExecutor executor : this.scripts ) {
			builder//.append( "(" )
				   .append( executor.getExecCommand() )
				   //.append( ")" )
				   ;

			if ( counter++ != ( this.scripts.size() - 1 ) ) {
				switch ( this.style ) {
				case EXCLUSIVE:
					builder.append(" && ");
				break;
				case INCLUSIVE:
					builder.append(" | ");
				break;
				}
			}
		}

		return builder.toString();
	}

	@Override
	public void addScript(BashScriptExecutor script) {
		this.scripts.add(script);
	}

	@Override
	public Collection<BashScriptExecutor> getScripts() {
		return this.scripts;
	}

	@Override
	public void setListStyle( ScriptListStyle style ) {
		this.style = style;
	}

	@Override
	public void kill() {
		for ( IScriptExecutor executor : this.getScripts() ) {
			executor.kill();
		}
	}

}
