package com.redshape.ui.application;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.ui.application
 * @date 2/7/12 {7:01 PM}
 */
public interface IBeansProvider {

    public <T> void inject( T instance );

    public <T> void inject( String name, T instance );

    public <T> T getBean( Class<T> clazz );
    
    public <T> T getBean( Class<T> clazz, String name );
    
    public <T> T getBean( String name );

}
