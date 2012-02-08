package com.redshape.ui.data.readers;

import com.redshape.ui.application.events.EventType;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 10.01.11
 * Time: 13:17
 * To change this template use File | Settings | File Templates.
 */
public class ReaderEvents extends EventType {

    public ReaderEvents( String code ) {
        super(code);
    }

    public static final ReaderEvents Processed = new ReaderEvents("ReaderEvents.Processed");

    public static final ReaderEvents Error = new ReaderEvents("ReaderEvents.Error");

}
