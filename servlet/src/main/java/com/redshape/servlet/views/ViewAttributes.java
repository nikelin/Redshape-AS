package com.redshape.servlet.views;

import com.redshape.utils.IEnum;

import java.util.HashMap;
import java.util.Map;

public class ViewAttributes implements IEnum<String> {
    /**
     * Registry for handling all attributes instances
     */
    private static final Map<String, ViewAttributes> REGISTRY = new HashMap<String, ViewAttributes>();

	private String name;
    private boolean translatable;
    private boolean isTransient;

    protected ViewAttributes( String name ) {
        this( name, false );
    }

    protected ViewAttributes( String name, boolean translatable ) {
        this( name, translatable, true );
    }

    protected ViewAttributes( String name, boolean translatable, boolean isTransient ) {
		this.name = name;
        this.translatable = translatable;
        this.isTransient = isTransient;

        REGISTRY.put( name, this );
	}

    public static class Env extends ViewAttributes {

        protected Env( String name ) {
            super(name, false);
        }

        public static final Env ResourcesHandler = new Env("Attribute.Env.ResourceHandler");
        public static final Env Controller = new Env("Attributes.Env.Controller");
        public static final Env Action = new Env("Attributes.Env.Action");

    }

    public static final ViewAttributes Exception = new ViewAttributes("Attribute.Page.Exception", true);
    public static final ViewAttributes Redirect = new ViewAttributes("Attribute.Page.Redirect", true);
	public static final ViewAttributes Title = new ViewAttributes("Attribute.Page.Title", true );
	public static final ViewAttributes Error = new ViewAttributes("Attribute.Error", true );

    @Override
	public String name() {
		return this.name;
	}

    public boolean isTransient() {
        return this.isTransient;
    }

    public boolean isTranslatable() {
        return this.translatable;
    }
	
	@Override
	public String toString() {
		return this.name();
	}

    public static ViewAttributes valueOf( String name ) {
        return REGISTRY.get( name );
    }
	
}
