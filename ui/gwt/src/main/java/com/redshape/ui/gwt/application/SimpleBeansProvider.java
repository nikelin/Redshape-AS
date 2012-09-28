package com.redshape.ui.gwt.application;

import com.redshape.ui.application.IBeansProvider;
import com.redshape.utils.Commons;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.ui.gwt.application
 * @date 2/8/12 {1:21 PM}
 */
public class SimpleBeansProvider implements IBeansProvider {
    private Map<BeanDescriptor, Object> registry = new HashMap<BeanDescriptor, Object>();
    
    public SimpleBeansProvider() {
        super();
    }

    public <T> void inject( String name, T instance ) {
        this.registry.put( new BeanDescriptor(name, instance.getClass()), instance );
    }

    public <T> void inject( T instance ) {
        this.registry.put( new BeanDescriptor(null, instance.getClass()), instance );
    }
    
    @Override
    public <T> T getBean(Class<T> clazz) {    	
        List<Object> matches = new ArrayList<Object>();
        for ( Map.Entry<BeanDescriptor, Object> entry : this.registry.entrySet() ) {
            BeanDescriptor descriptor = entry.getKey();
            if ( descriptor.isMatch(clazz) ) {
                matches.add( entry.getValue() );
            }
        }

        if ( matches.size() > 1 ) {
            throw new IllegalArgumentException("There is ambiguous choice for the given class "
                    + clazz.getName() );
        }

        if ( matches.isEmpty() ) {
            throw new IllegalArgumentException("Bean with such type not exists in registry");
        }
        
        return (T) Commons.firstOrNull(matches);
    }

    @Override
    public <T> T getBean(Class<T> clazz, String name) {
        Object bean = this.getBean(name);
        if ( !clazz.equals( bean.getClass() ) )  {
            throw new IllegalArgumentException("Unable to convert class " + clazz.getName()
                    + " to " + bean.getClass().getName() );
        }

        return (T) bean;
    }

    @Override
    public <T> T getBean(String name) {
        for ( Map.Entry<BeanDescriptor, Object> entry : this.registry.entrySet() ) {
            String beanName = entry.getKey().getName();
            if ( beanName != null && beanName.equals(name) ) {
                return  (T) entry.getValue();
            }
        }

        throw new IllegalArgumentException("Bean with such name not exists in registry");
    }
    
    protected class BeanDescriptor {
        private String name;
        private Class<?> clazz;
        
        public BeanDescriptor( String name, Class<?> clazz ) {
            this.name = name;
            this.clazz = clazz;
        }
        
        public boolean isMatch( Class<?> clazz ) {
        	if ( this.isAncestor(clazz, this.getClazz() ) ) {
                return true;
            }
            
            if ( this.isAncestor(this.getClazz(), clazz) ) {
                return true;
            }
               
            return false;
        }
        
        protected boolean isAncestor( Class<?> source, Class<?> target ) {
            Class<?> parent = source;
            do {
                if ( parent.equals( target )
                        || parent.toString().equals( target.toString() )
                        || parent.toString().replace("interface", "")
                        					.trim()
                    							.equals( this.getName() ) ) {
                    return true;
                }
            } while ( null != ( parent = parent.getSuperclass() ) );

            return false;
        } 
        
        public String getName() {
            return name;
        }

        public Class<?> getClazz() {
            return clazz;
        }
    }

}
