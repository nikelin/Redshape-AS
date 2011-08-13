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
