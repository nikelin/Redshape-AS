package com.redshape.ui.data;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 10.01.11
 * Time: 13:08
 * To change this template use File | Settings | File Templates.
 */
public interface IModelData {
	
	public void makeDirty( boolean isDirty );
	
	public boolean isDirty();

}
