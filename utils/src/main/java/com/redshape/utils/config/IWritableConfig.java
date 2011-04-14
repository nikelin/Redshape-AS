package com.redshape.utils.config;

public interface IWritableConfig extends IConfig {

	public boolean isWritable();

	public IWritableConfig append( IConfig config );
	
	public IWritableConfig makeWritable(boolean value);

	public IWritableConfig set(String value) throws ConfigException;

	public IWritableConfig attribute(String name, String value);

	public IWritableConfig createChild(String name);

	public IWritableConfig remove() throws ConfigException;

}