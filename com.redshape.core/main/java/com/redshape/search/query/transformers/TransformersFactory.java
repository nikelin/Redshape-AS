package com.redshape.search.query.transformers;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 30, 2010
 * Time: 3:13:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class TransformersFactory {
    private static TransformersFactory defaultInstance = new TransformersFactory();
    private Class<? extends IQueryTransformer> defaultTransformer;

    public static void setDefault( TransformersFactory factory ) {
        defaultInstance = factory;
    }

    public static TransformersFactory getDefault() {
        return defaultInstance;
    }

    public void setDefaultTransformer( Class<? extends IQueryTransformer> clazz ) {
        this.defaultTransformer = clazz;
    }

    public IQueryTransformer createTransformer() throws InstantiationException {
        return this.createTransformer( this.defaultTransformer );
    }

    public <T extends IQueryTransformer> T createTransformer( Class<? extends T> clazz ) throws InstantiationException {
        try {
            return clazz.newInstance();
        } catch ( Throwable e ) {
            throw new InstantiationException();
        }
    }

}
