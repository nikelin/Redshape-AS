package com.redshape.daemon.jobs;

import java.net.URI;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 15.06.11
 * Time: 14:02
 * To change this template use File | Settings | File Templates.
 */
public interface IJobDestination {

    public void setAddress( URI address );

    public URI getAddress();

    public void setLastPingDate( Date date );

    public Date getLastPingDate();

    public Integer getAverageJobsCount();

}
