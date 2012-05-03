package com.redshape.search.index.builders;

import com.redshape.search.index.visitor.field.IFieldVisitor;
import com.redshape.search.index.visitor.field.StandardFieldVisitor;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Aug 13, 2010
 * Time: 3:18:51 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractIndexBuilder implements IIndexBuilder, ApplicationContextAware {
    private IFieldVisitor fieldVisitor;
    private ApplicationContext applicationContext;

    protected ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public IFieldVisitor getFieldVisitor() {
        if ( this.fieldVisitor == null ) {
            this.fieldVisitor = new StandardFieldVisitor();
            this.getApplicationContext().getAutowireCapableBeanFactory().autowireBean(this.fieldVisitor);
        }

        return this.fieldVisitor;
    }

    @Override
    public void setFieldVisitor( IFieldVisitor visitor ) {
        this.fieldVisitor = visitor;
    }

}
