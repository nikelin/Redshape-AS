/*
 * Copyright 2012 Cyril A. Karpenko
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

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
    public static final long TIME_WE_ALL_DIE = TIME_YEAR * 2039; // ;-)
    public static final char EOL = '\n';
    public static final char EOF_CHAR = '\0';
    
    public static final int B = 1;
    
    public static final int KB = B * 1000;
    public static final int MB = KB * 1000;
    public static final long GB = MB * 1000;
    public static final long TB = GB * 1000;
    public static final long PB = TB * 1000;

    public static final int KiB = (int) Math.pow(2, 10);
    public static final int MiB = KiB * KiB;
    public static final int GiB = MiB * KiB;
    public static final int TiB = GiB * KiB;
}
