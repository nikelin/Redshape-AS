package com.redshape.ui.application;

import com.redshape.applications.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.io.File;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.ui.application
 * @date 2/7/12 {8:55 PM}
 */
public class SpringContextProvider implements IBeansProvider {

    private String contextPath;
    private ApplicationContext context;
    
    public SpringContextProvider() {
        this( System.getProperty(SpringApplication.SPRING_CONTEXT_PARAM) );
    }

    public SpringContextProvider( String contextPath ) {
        super();

        this.contextPath = contextPath;
        if ( this.contextPath == null ) {
            this.contextPath = SpringApplication.DEFAULT_CONTEXT_PATH;
        }
        
        this.loadContext();
    }
    
    public void changeContextPath( String path ) {
        this.contextPath = path;
        this.loadContext();
    }
    
    protected void loadContext() {
        File file = new File( this.contextPath );
        if ( file.exists() ) {
            this.context = new FileSystemXmlApplicationContext(this.contextPath);
        } else {
            this.context = new ClassPathXmlApplicationContext(this.contextPath);
        }
    }

    @Override
    public <T> void inject(T instance) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> void inject(String name, T instance) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> T getBean(Class<T> clazz) {
        return this.context.getBean(clazz);
    }

    @Override
    public <T> T getBean(Class<T> clazz, String name) {
        return this.context.getBean(name, clazz);
    }

    @Override
    public <T> T getBean(String name) {
        return (T) this.context.getBean(name);
    }
}
