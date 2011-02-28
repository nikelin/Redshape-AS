package com.redshape.io.protocols.core.writers;

import com.redshape.io.protocols.core.response.IResponse;

import java.io.OutputStream;
import java.util.Collection;

/**
 * WebCam Project
 *
 * @author nikelin
 * @project vio
 * @package com.vio.api.io.writers
 * @date Apr 1, 2010
 */
public interface IResponseWriter<T extends IResponse> extends IWriter {

    /**
     * Метод отправки композитного отклика
     * @param source
     * @param responses
     * @throws WriterException
     */
    public void writeResponse( OutputStream source, Collection<T> responses ) throws WriterException;

    /**
     * Метод отправки отклика ввиде ошибки проводки
     * @param source
     * @param exception
     * @throws WriterException
     */
    public void writeResponse( OutputStream source, Throwable exception ) throws WriterException;

    /**
     * Метод отправки обычного отклика
     * @param source
     * @param response
     * @throws WriterException
     */
    public void writeResponse( OutputStream source, T response) throws WriterException;

}
