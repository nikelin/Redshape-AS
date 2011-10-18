package com.redshape.plugins.meta;

import java.util.Date;
import java.net.URI;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.plugins.meta
 * @date 10/11/11 1:17 PM
 */
public interface IPublisherInfo {

	public String getCompany();

	public Date getPublishedOn();

	public URI getURI();

}
