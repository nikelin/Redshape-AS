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

package com.redshape.servlet.core;

import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.*;

/**
* Created with IntelliJ IDEA.
* User: cyril
* Date: 4/26/12
* Time: 10:05 PM
* To change this template use File | Settings | File Templates.
*/
public class MultipartRequest implements IMultipartRequest {
    private MimeMultipart mimeparts;  // To hold the parsed results
    private HashMap params = new HashMap();  // To hold the uploaded parameters
    private HashMap files = new HashMap();  // To hold the uploaded files
    private byte [] buf = new byte[8096]; // Scratch buffer space

    /**
     * This private inner class is a simple implementation of the
     * DataSource interface. It provides the bridge between the
     * HttpServletRequest and the Java Mail classes.
     */
    private class Source implements javax.activation.DataSource{
        private InputStream stream;
        private String mimetype;

        Source(HttpServletRequest req) throws IOException {
            stream = req.getInputStream();
            mimetype = req.getHeader("CONTENT-TYPE");
        }

        public InputStream getInputStream(){ return stream; }
        public String getContentType(){ return mimetype; }
        public OutputStream getOutputStream(){throw new RuntimeException();} // Not used
        public String getName(){throw new RuntimeException();} // Not used
    };

    /**
     * This public inner class is used to store information about uploaded
     * files.  Users of the MultipartRequest class should generally
     * refer to this class as MultipartRequest.FileInfo (as per usual
     * Java rules).
     */
    public class FileInfo implements IFileInfo {
        private byte[] content;  // The byte-copy of the file's contents
        private String sourcename; // The name of the file on the browser's system
        private String contentType; // The mimetype supplied by the browser
        public FileInfo(byte [] content, String sourcename, String contentType){
            this.content = content;
            this.sourcename = sourcename;
            this.contentType = contentType;
        }
        public byte [] getContent(){ return content; }
        public String getSourceFilename(){ return sourcename; }
        public String getContentType(){ return contentType; }
        public void setSourceFileName(String fileName) { this.sourcename = fileName; };
    }

    /**
     * The constructor. This accepts an HttpServletRequest (which it assumes to be
     * from a post of a ENCTYPE="multipart/form-data" form) and parses all the
     * information into a MimeMultipart object.
     * <p>
     * It then iterates through that parsed object, extracting the parameters and
     * files from it for the user.
     *
     * @param req a request from a form post with ENCTYPE="multipart/form-data".
     * @throws javax.mail.MessagingException if there are problems with parsing the MIME
     *      information.
     * @throws java.io.IOException if there are problems reading the input stream.
     */
    public MultipartRequest(HttpServletRequest req) throws MessagingException, IOException {
        // Here's the line which does all of the parsing.
        // The request size and content type could be checked before calling, if desired.
        mimeparts = new MimeMultipart( new Source(req) );

        // Now iterate over the parsed results
        int partCount = mimeparts.getCount();
        for(int i=0; i<partCount; ++i){
            MimeBodyPart bp = (MimeBodyPart) mimeparts.getBodyPart(i);
            String disposition = bp.getHeader("Content-Disposition","");
            // I use the filename to indicate if this is a file or a parameter.
            // Could instead use bp.getContent().getClass() to indicate if we
            // have a String, an InputStream, or a (nested) MultiPart.
            String filename = bp.getFileName(); // This filename appears to lack "\" chars.
            if( filename == null ) doParameter(bp, disposition);
            else doFile(bp, disposition);
        }
    }

    /**
     * @return an iterator for the parameter names, as per servlet 2.2 spec
     *      except for using Iterator rather than Enumeration.
     */
    @Override
    public Iterator getParameterNames(){
        return params.keySet().iterator();
    }

    /**
     * Return the (only) parameter with the given name, or null
     * if no such parameters exist. If there are more than one
     * parameters with this name, return the first (per servlet 2.2 API)
     *
     * @param name the HTML name of the input field for the parameter
     * @return the value of the parameter with the given name
     */
    @Override
    public String getParameter(String name){
        List valuelist = (List) params.get(name);
        if( valuelist == null ) return null;
        return (String) valuelist.get( 0 );  // Return first value, as per servlet 2.2 API
    }

    /**
     * Return an array of all the parameters with the given name,
     * or null if no parameters with this name exist.
     *
     * @param name the HTML name of the input field for the parameter
     * @return the array of values of parameters with this name.
     */
    @Override
    public String [] getParameterValues(String name){
        List valuelist = (List) params.get(name);
        if( valuelist == null ) return null;
        return (String[]) valuelist.toArray( new String[valuelist.size()] );
    }

    /**
     * @return an Iterator for the FileInfo items describing the
     * files encapsulated in the request.
     */
    @Override
    public Iterator getFileInfoNames(){
        return files.keySet().iterator();
    }

    /**
     * Return the (only) FileInfo object describing the uploaded
     * files with a given HTML name, or null if no such name exists
     * in the request.  If there are several files uploaded under the
     * name, return the first.
     *
     * @param name the HTML name of the input field for the file
     * @return the FileInfo object for the file.
     */
    @Override
    public IFileInfo getFileInfo(String name){
        List filelist = (List) files.get(name);
        if( filelist == null ) return null;
        return (FileInfo) filelist.get( 0 );
    }

    /**
     * Return an array of all the FileInfo objects representing the
     * files uploaded under the given HTML name, or null if no such
     * name exists.
     *
     * @param name the HTML name of the input field for the files
     * @return the array of FileInfo objects for files uploaded
     *      under this name.
     */
    @Override
    public IFileInfo [] getFileInfoValues(String name){
        List filelist = (List) files.get(name);
        if( filelist == null ) return null;
        return (FileInfo[]) filelist.toArray( new FileInfo[filelist.size()] );
    }

    /**
     * Do whatever processing is needed for a parameter.
     */
    private void doParameter(MimeBodyPart bp, String disposition) throws MessagingException, IOException {
        String name = findValue("name", disposition);
        String value = (String) bp.getContent();
        List valuelist = (List) params.get(name);
        if( valuelist==null ){
            valuelist = new LinkedList();
            params.put(name, valuelist);
        }
        valuelist.add(value);
    }

    /**
     * Do whatever processing is needed for a file.
     */
    private void doFile(MimeBodyPart bp, String disposition) throws MessagingException, IOException {
        String name = findValue("name", disposition);
        String filename = findValue("filename", disposition);
        if ( filename != null ) filename = new File(filename).getName();
        BufferedInputStream in = new  BufferedInputStream(bp.getInputStream());
        ByteArrayOutputStream out = new ByteArrayOutputStream( in.available() );
        int k;
        while( (k=in.read(buf)) != -1 ) out.write(buf,0,k);
        out.close();
        FileInfo f = new FileInfo(out.toByteArray(), filename, bp.getContentType());
        List filelist = (List) files.get(name);
        if( filelist==null ){
            filelist = new LinkedList();
            files.put(name, filelist);
        }
        filelist.add(f);
    }

    /**
     * Utiltity to extract a parameter value from a header line, since the
     * Java library routines don't seem to let us do that.
     */
    private String findValue(String parm, String header){
        StringTokenizer st = new StringTokenizer(header, "; =");
        while( st.hasMoreTokens() ){
            String token = st.nextToken();
            if( token.equalsIgnoreCase(parm) ){
                try { return st.nextToken("\"="); }
                catch( NoSuchElementException e ){ return ""; } // e.g. filename=""
            }
        }
        return null;
    }
}
