package com.redshape.auth.annotations;

import com.redshape.auth.IIdentity;

public @interface TargetIdentity {
	
	Class<? extends IIdentity> identity();
}
