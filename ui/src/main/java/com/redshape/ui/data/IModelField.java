package com.redshape.ui.data;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 10.01.11
 * Time: 19:28
 * To change this template use File | Settings | File Templates.
 */
public interface IModelField {

	public void setTitle( String title );
	
	public String getTitle();
	
    public void setType( Class<?> type );

    public Class<?> getType();

    public void setRequired( boolean required );

    public boolean isRequired();

    public String getName();

    public void setFormat( String format );

    public String getFormat();

}
