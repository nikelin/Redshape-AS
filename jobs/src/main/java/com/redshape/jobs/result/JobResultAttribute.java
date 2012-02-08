package com.redshape.jobs.result;

import com.redshape.utils.IEnum;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.daemon.jobs.result
 * @date 9/18/11 3:22 AM
 */
public class JobResultAttribute implements IEnum<String> {
	private static final long serialVersionUID = 7809277796267471649L;

	private String name;

	protected JobResultAttribute( String name ) {
		this.name = name;
	}

	@Override
	public String name() {
		return this.name;
	}
}
