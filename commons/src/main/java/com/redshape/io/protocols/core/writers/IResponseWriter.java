package com.redshape.io.protocols.core.writers;

import com.redshape.io.protocols.core.response.IResponse;
import com.redshape.io.protocols.core.sources.output.OutputSource;
import com.redshape.exceptions.ExceptionWithCode;

import java.util.Collection;

/**
 * WebCam Project
 *
 * @author nikelin
 * @project vio
 * @package com.vio.api.io.writers
 * @date Apr 1, 2010
 */
public interface IResponseWriter<T extends OutputSource> extends Writer<T> {

    /**
     * Метод отправки композитного отклика
     * @param source
     * @param responses
     * @throws WriterException
     */
    public void writeResponse( T source, Collection<? extends IResponse> responses ) throws WriterException;

    /**
     * Метод отправки отклика ввиде ошибки проводки
     * @param source
     * @param exception
     * @throws WriterException
     */
    public void writeResponse( T source, ExceptionWithCode exception ) throws WriterException;

    /**
     * Метод отправки обычного отклика
     * @param source
     * @param response
     * @throws WriterException
     */
    public void writeResponse( T source, IResponse response) throws WriterException;

}
