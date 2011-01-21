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
