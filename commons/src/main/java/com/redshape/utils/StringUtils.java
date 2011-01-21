package com.redshape.utils;

import sun.net.util.IPAddressUtil;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class StringUtils {
    public static List<String> camelCaseDelimiters = Arrays.asList( "_", "-" );

    public static String repeat(String source, int times) {
        String result = "";

        while (times > 0) {
            result = result.concat(source);
            times -= 1;
        }

        return result;
    }

    public static String reverseSentence( String sentence ) {
        StringBuilder builder = new StringBuilder();
        for ( String sentencePart : sentence.split(" ") ) {
            builder.append(sentencePart);
        }
        return builder.reverse().toString();
    }

    public static String reverse( String input ) {
          return String.valueOf( reverse( input.toCharArray() ) );
    }

    public static char[] reverse( char[] input ) {
        char[] result = new char[input.length];

        int lastOffset = input.length - 1;
        for( int i = lastOffset; i >= 0; i-- ) {
            result[lastOffset - i] = input[i];
        }

        return result;
    }

    public static String getFileExtension( File file ) {
        return getFileExtension( file.getPath() );
    }

    public static String getFileExtension( String name ) {
        return name.substring( name.lastIndexOf(".") + 1, name.length() );
    }

    public static String toCamelCase( String name ) {
        return toCamelCase( name, true );
    }

    /**
* Camelize input string
* @param name Input string
* @param ucfirst Make first character uppercased
* @return String
*/
    public static String toCamelCase( String name, boolean ucfirst ) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < name.length(); i++) {
            String prevChar = name.substring(i > 0 ? i - 1 : 0, i > 0 ? i : 1);
            String currChar = name.substring(i, i + 1);

            if ( camelCaseDelimiters.contains( prevChar ) || ( ucfirst && i == 0 && !camelCaseDelimiters.contains( currChar ) ) ) {
                result.append( currChar.toUpperCase() );
            } else if ( !camelCaseDelimiters.contains( currChar ) ) {
                result.append(currChar);
            }
        }

        return result.toString();
    }

    public static String fromCamelCase( String name, String delimiter ) {
        StringBuilder result = new StringBuilder();

        int last_delimiter_pos = 0;
        for ( int i = 0; i < name.length(); i++ ) {
            String currChar = name.substring(i, i + 1);

            if ( currChar.toUpperCase().equals( currChar ) && i != last_delimiter_pos - 1 ) {
                if ( i > 0 ) {
                    result.append( delimiter );
                    last_delimiter_pos = i;
                }

                result.append( currChar.toLowerCase() );
            } else {
                result.append( currChar.toLowerCase() );
            }
        }

        return result.toString();
    }

    public static String join( List<?> join, String separator ) {
        return join( join.toArray( new Object[join.size()] ), separator );
    }

    public static String join( Object[] join, String separator ) {
        StringBuilder builder = new StringBuilder();
        int i = 0;
        for ( Object joinItem : join ) {
            builder.append( String.valueOf( joinItem ) );

            if ( i++ != join.length - 1 ) {
                builder.append( separator );
            }
        }

        return builder.toString();
    }


    public static byte[] stringToIP( String addrString ) {
        return IPAddressUtil.textToNumericFormatV4(addrString);
    }

    public static String IPToString( byte[] address ) {
        String[] result = new String[address.length];
        for ( int i = 0; i < address.length; i++ ) {
            result[i] = String.valueOf( address[i] & 0xff );
        }

        return StringUtils.join( result, "." );
    }


}
