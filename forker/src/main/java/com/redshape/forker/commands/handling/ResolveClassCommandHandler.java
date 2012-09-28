package com.redshape.forker.commands.handling;

import com.redshape.forker.IForkCommand;
import com.redshape.forker.IForkCommandResponse;
import com.redshape.forker.ProcessException;
import com.redshape.forker.commands.ResolveClassCommand;
import com.redshape.forker.handlers.IForkCommandHandler;
import com.redshape.utils.StringUtils;

import java.io.InputStream;
import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: cyril
 * Date: 9/18/12
 * Time: 2:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class ResolveClassCommandHandler implements IForkCommandHandler {

    @Override
    public boolean isSupported(IForkCommand command) {
        return command instanceof ResolveClassCommand.Request;
    }

    @Override
    public IForkCommandResponse execute(IForkCommand command) throws ProcessException {
        ResolveClassCommand.Request request = (ResolveClassCommand.Request) command;

        ResolveClassCommand.Response response;
        try {
            Class<?> classObject = this.getClass().getClassLoader().loadClass( request.getCanonicalName() );

            URL classObjectLocation = classObject.getProtectionDomain().getCodeSource().getLocation();
            if ( classObjectLocation.getPath().endsWith("jar") ) {
                classObjectLocation = new URL(
                    "jar:file:" + classObjectLocation.getPath() + "!/"
                        + StringUtils.preparePathByClass(classObject)
                );
            }

            InputStream inputStream = classObjectLocation.openStream();
            byte[] buff = new byte[inputStream.available()];
            inputStream.read(buff, 0, buff.length);

            response = new ResolveClassCommand.Response(IForkCommandResponse.Status.SUCCESS);
            response.setCanonicalName(request.getCanonicalName());
            response.setClazzData( buff );
        } catch ( Throwable e ) {
            response = new ResolveClassCommand.Response(IForkCommandResponse.Status.FAIL);
        }

        return response;
    }
}