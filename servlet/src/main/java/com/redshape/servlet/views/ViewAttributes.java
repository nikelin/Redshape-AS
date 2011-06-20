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
    private boolean  translatable;

    protected ViewAttributes( String name ) {
        this( name, false );
    }

    protected ViewAttributes( String name, boolean translatable ) {
		this.name = name;
        this.translatable = translatable;

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
	
	public static final ViewAttributes PAGE_TITLE = new ViewAttributes("Attribute.Page.Title", true );
	public static final ViewAttributes ERROR = new ViewAttributes("Attribute.Error", true );

    @Override
	public String name() {
		return this.name;
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
