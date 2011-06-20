package com.redshape.daemon;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.ws.WebFault;

@WebFault(name="DaemonException", faultBean ="com.api.navirara.com.redshape.daemon.DaemonException")
@XmlAccessorType(XmlAccessType.FIELD)
public class DaemonException extends Exception {
	private static final long serialVersionUID = -3237466176609570900L;
	
    public DaemonException() {
        this(null);
    }

    public DaemonException(String message) {
        this(message, null );
    }

    public DaemonException(String message, Throwable cause) {
        super(message, cause);
    }

}
