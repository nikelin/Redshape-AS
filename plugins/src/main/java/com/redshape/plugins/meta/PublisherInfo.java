package com.redshape.plugins.meta;

import com.redshape.plugins.meta.IPublisherInfo;

import java.net.URI;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Nikelin
 * Date: 09.01.12
 * Time: 18:31
 * To change this template use File | Settings | File Templates.
 */
class PublisherInfo implements IPublisherInfo {
    private String company;
    private Date publishedOn;
    private URI uri;

    public PublisherInfo() {
        super();
    }

    @Override
    public void setCompany(String value) {
        this.company = value;
    }

    @Override
    public String getCompany() {
        return this.company;
    }

    @Override
    public void setPublishedOn(Date date) {
        this.publishedOn = date;
    }

    @Override
    public Date getPublishedOn() {
        return this.publishedOn;
    }

    @Override
    public void setURI(URI uri) {
        this.uri = uri;
    }

    @Override
    public URI getURI() {
        return this.uri;
    }
}
