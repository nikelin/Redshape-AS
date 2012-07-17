package com.redshape.utils;

import sun.net.util.IPAddressUtil;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;


@SuppressWarnings("restriction")
public class StringUtils extends SimpleStringUtils {

    public static List<String> camelCaseDelimiters = Arrays.asList( "_", "-" );
    private static final String RANDOM_STRING_SOURCE = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final String ESCAPE_SYMBOL = "\\";

    public static final Date parseDate( String dateFormat, String dateString ) throws ParseException {
        return new SimpleDateFormat(dateFormat).parse(dateString);
    }

    public static final String escapePath( String path ) {
        return path.replaceAll("(\\/|\\\\)", "\\" + File.separator );
    }

    public final static String prepareClassByPath( String path ) {
        return path.substring(path.lastIndexOf('.')).replaceFirst("[\\.]", "");
    }
    
    public final static String preparePathByClass( Class<?> clazz ) {
        return preparePathByClass( clazz.getCanonicalName() );
    }

    protected final static String preparePathByPackage( String packageName ) {
        return packageName.replaceAll(Pattern.quote("."), "\\" + File.separator );
    }

    public final static String preparePathByClass( String className ) {
        return preparePathByPackage(className) + ".class";
    }

    public final static byte[] stringToIP( String addrString ) {
        Commons.checkArgument( addrString.isEmpty(), "Empty address given");

        return IPAddressUtil.textToNumericFormatV4(addrString);
    }

    public final static Integer hex( String data ) {
        return Integer.valueOf(
            String.format("%x", new BigInteger(1, data.getBytes( Charset.forName("UTF-8") ) ) )
        );
    }

    public final static String formatDate( String format, Date date ) {
        return new SimpleDateFormat(format).format(date);
    }

    public final static String stackTraceAsString( Throwable e ) {
        final Writer writer = new StringWriter();
        final PrintWriter printer = new PrintWriter(writer);

        e.printStackTrace(printer);

        return writer.toString();
    }

}
