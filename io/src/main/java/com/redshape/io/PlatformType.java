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

package com.redshape.io;


/**
 * Created by IntelliJ IDEA.
 * User: cwiz
 * Date: 03.12.10
 * Time: 16:17
 * To change this template use File | Settings | File Templates.
 */

import java.util.Arrays;

public enum PlatformType {
    WINNT,
    WINXP,
    WIN2K,
    UNIX,
    FREEBSD,
    LINUX,
    UNKNOWN;

    private static PlatformType[] UNIX_FAMILY = { UNIX, FREEBSD, LINUX };
    private static PlatformType[] WIN_FAMILY = { WINNT, WINXP, WIN2K };

    /**
         * @todo: Very bad names detector... needs to rewrite
         * @param platformName
         * @return
         */
    public static PlatformType detectFamily( String platformName ) {
        final String normalizedName = platformName.toLowerCase();
        if ( normalizedName.contains("windows") ) {
            return WINNT;
        } else if ( normalizedName.contains("freebsd") || normalizedName.contains("linux") || normalizedName.contains("unix")  ) {
            return UNIX;
        }

        return UNKNOWN;
    }

    public static boolean isInFamily( PlatformType type, PlatformType familyType ) {
        switch ( familyType ) {
            case WINNT:
                return Arrays.binarySearch(WIN_FAMILY, type) != -1;
            case UNIX:
                return Arrays.binarySearch( UNIX_FAMILY, type ) != -1;
        }

        return false;
    }
}
