package com.redshape.utils;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jan 14, 2010
 * Time: 1:34:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class Constants {
    public static final int TIME_MILLISECOND = 1;
    public static final int TIME_SECOND = TIME_MILLISECOND * 1000;
    public static final int TIME_MINUTE = TIME_SECOND * 60;
    public static final long TIME_HOUR = TIME_MINUTE * 60;
    public static final long TIME_DAY = TIME_HOUR * 24;
    public static final long TIME_WEEK = TIME_DAY * 7;
    public static final long TIME_MONTH = TIME_DAY * 30;
    public static final long TIME_YEAR = TIME_MONTH * 12;
    public static final long TIME_WE_ALL_DEATH = TIME_YEAR * 2039; // ;-)
    public static final char EOL = '\n';
    public static final char EOF_CHAR = '\0';
    
    public static final int B = 1;
    
    public static final int KB = B * 1000;
    public static final int MB = KB * 1000;
    public static final long GB = MB * 1000;
    public static final long TB = GB * 1000;
    public static final long PB = TB * 1000;

    public static final int KiB = 2 ^ 10;
    public static final int MiB = KiB * KiB;
    public static final int GiB = MiB * KiB;
    public static final int TiB = GiB * KiB;
}
