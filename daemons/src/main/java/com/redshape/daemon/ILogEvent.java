package com.redshape.daemon;

import java.util.Date;

/**
 * @author nikelin
 */
public interface ILogEvent extends IDaemonEvent {

    public Date getDateTime();

    public String getWork();

    public void setWork(String work);
    
    public String getMessage();

    public String getSeverety();

    public void setDateTime(final Date dateTime);

    public void setMessage(final String message);

    public void setSeverety(final String severety);

    public void setMessageClass(String messageClass);

    public String getMessageClass();
}
