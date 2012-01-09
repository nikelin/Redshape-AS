package com.redshape.plugins.meta;

import java.util.Date;
import java.net.URI;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.plugins.meta
 * @date 10/11/11 1:17 PM
 */
public interface IPublisherInfo {

    public void setCompany( String value );
    
	public String getCompany();

    public void setPublishedOn( Date date );
    
	public Date getPublishedOn();
    
    public void setURI( URI uri );

	public URI getURI();

}
