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

package com.redshape.ui.utils;

import com.redshape.utils.IEnum;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 03.01.11
 * Time: 21:23
 * To change this template use File | Settings | File Templates.
 */
public final class UIConstants {
	public interface Attribute extends IEnum<String> {
	}
	
	public enum Area implements Attribute {
		NONE,
		SOUTH,
		NORTH,
		CENTER,
		EAST,
		WEST,
		MENU
	}
	
	public enum System implements Attribute {
        WINDOW,
        MENUBAR,
        VIEW_RENDERER,
        APP_CONTEXT
	}
}
