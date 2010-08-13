package com.vio.auth.annotations;

import com.vio.auth.IIdentity;

public @interface TargetIdentity {
	
	Class<? extends IIdentity> identity();
}
