package com.redshape.notifications;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Oct 7, 2010
 * Time: 7:09:30 PM
 * To change this template use File | Settings | File Templates.
 */
public final class NotificationFlag {
    public final static NotificationFlag IMPORTANT = new NotificationFlag(0);
    public final static NotificationFlag NEW = new NotificationFlag(2);
    public final static NotificationFlag ARCHIVED = new NotificationFlag(4);
    public final static NotificationFlag DELETED = new NotificationFlag(8);

    private final static NotificationFlag[] values = new NotificationFlag[] { IMPORTANT, NEW, ARCHIVED, DELETED };
    private final static int LAST_BIT = values.length != 0 ? values[values.length].value() : 0;

    private int value;

    private NotificationFlag( int value ) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }

    public static NotificationFlag valueOf( int value ) {
        for ( NotificationFlag flag : values ) {
            if ( flag.value() == value ) {
                return flag;
            }
        }

        return null;
    }

    public static NotificationFlag[] values() {
        return values;
    }

    public static int getLastBit() {
        return LAST_BIT;
    }
}
