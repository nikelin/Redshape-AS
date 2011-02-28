package com.redshape.ui.data;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 10.01.11
 * Time: 19:28
 * To change this template use File | Settings | File Templates.
 */
public interface IModelField {

	public boolean isTransient();
	
	public IModelField markList( boolean value );
	
	public boolean isList();
	
	public IModelField makeTransient( boolean value );
	
	public IModelField setTitle( String title );
	
	public String getTitle();
	
    public IModelField setType( Class<?> type );

    public Class<?> getType();

    public IModelField setRequired( boolean required );

    public boolean isRequired();

    public String getName();

    public IModelField setFormat( String format );

    public String getFormat();

}
