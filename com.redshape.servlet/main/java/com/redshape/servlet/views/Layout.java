package com.redshape.servlet.views;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 10/11/10
 * Time: 1:56 AM
 * To change this template use File | Settings | File Templates.
 */
public class Layout extends AbstractView implements ILayout {
    private IView content;

    public Layout( String basePath, String viewPath, String extension ) {
        super(basePath, viewPath, extension);
    }

    public void setContent( IView content ) {
        this.content = content;
    }

    public IView getContent() {
        return this.content;
    }

}
