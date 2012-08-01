package com.redshape.utils.config.sources;

import com.redshape.utils.Commons;
import com.redshape.utils.config.ConfigException;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: cyril
 * Date: 8/1/12
 * Time: 7:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class StringConfigSource implements IConfigSource {

    public static class OnChangeAdapter implements OnChangeCallback, Serializable {
        public OnChangeAdapter() {
        }

        @Override
        public void onChanged() {
            throw new UnsupportedOperationException("Not implemented");
        }
    }

    private OnChangeCallback callback;
    private boolean clean;
    private String[] data;
    private int offset;

    public StringConfigSource() {
        this(new String[] {});
    }

    public StringConfigSource(String[] data) {
        Commons.checkNotNull(data);

        this.data = data;
    }

    private void checkOffset() {
        Commons.checkArgument( this.offset < this.data.length && this.offset >= 0 );
    }

    @Override
    public void setCallback(OnChangeCallback callback) {
        this.callback = callback;
    }

    @Override
    public void markClean() {
        this.clean = true;
    }

    @Override
    public void reload() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public boolean isChanged() {
        return this.clean;
    }

    @Override
    public String read() throws ConfigException {
        checkOffset();
        return this.data[this.offset++];
    }

    @Override
    public void write(String data) throws ConfigException {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public boolean isWritable() {
        return false;
    }

    @Override
    public boolean isReadable() {
        return true;
    }
}
