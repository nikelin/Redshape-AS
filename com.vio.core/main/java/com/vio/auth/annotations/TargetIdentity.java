package com.vio.auth.annotations;

import com.vio.auth.Identity;

public @interface TargetIdentity {
	
	Class<? extends Identity> identity();
}
