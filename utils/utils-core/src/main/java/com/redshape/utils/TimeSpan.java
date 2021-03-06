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

package com.redshape.utils;

import java.util.concurrent.TimeUnit;

/**
 * Created by IntelliJ IDEA.
 * User: Jellical
 * Date: 11.04.11
 * Time: 11:44
 * To change this template use File | Settings | File Templates.
 */
public class TimeSpan {
	private Long value;
	private TimeUnit type;

	public TimeSpan(Integer value, TimeUnit type) {
		this( value.longValue(), type );
	}

	public TimeSpan(Long value, TimeUnit type) {
		this.value = value;
		this.type = type;
	}

	public Long getValue() {
		return this.value;
	}

	public TimeUnit getType() {
		return this.type;
	}

}
