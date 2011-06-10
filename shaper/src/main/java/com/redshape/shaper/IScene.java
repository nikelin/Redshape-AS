package com.redshape.shaper;

import com.redshape.shaper.geom.Point3D;

public interface IScene {

	public void addObject( ISceneObject object, Point3D point );
	
	public void removeObject( ISceneObject object, Point3D point );
	
}
