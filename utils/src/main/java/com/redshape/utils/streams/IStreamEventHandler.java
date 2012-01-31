package com.redshape.utils.streams;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.utils.streams
 * @date 1/31/12 {5:10 PM}
 */
public interface IStreamEventHandler {
    
    public void onData( InputStream stream ) throws IOException;
    
    public void onClosed( InputStream stream );
    
}
