package com.redshape.ui.data.loaders;

import com.redshape.io.protocols.core.IProtocol;
import com.redshape.io.protocols.core.readers.IRequestReader;
import com.redshape.io.protocols.core.request.IRequest;
import com.redshape.io.protocols.core.sources.input.InputSource;
import com.redshape.ui.events.IEventDispatcher;
import com.redshape.ui.data.IModelData;
import com.redshape.ui.data.readers.IDataReader;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 10.01.11
 * Time: 13:12
 * To change this template use File | Settings | File Templates.
 */
public interface IDataLoader<T extends IModelData> extends IEventDispatcher {

    public void load() throws LoaderException;

}