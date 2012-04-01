package com.redshape.persistence;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.persistence
 * @date 2/6/12 {6:13 PM}
 */
public class DaoContextHolder implements ApplicationContextAware {
    private static final Object lock = new Object();
    private static DaoContextHolder instance;

    private ApplicationContext context;
    
    public DaoContextHolder() {
        synchronized (lock) {
            if ( instance != null ) {
                return;
            }

            instance = this;
        }
    }
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
    
    public ApplicationContext getContext() {
        return this.context;
    }
    
    public static final DaoContextHolder instance() {
        return instance;
    }
}