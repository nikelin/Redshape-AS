package com.redshape.utils.clonners;

import org.apache.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class DeepClonner implements IObjectsClonner {
	private static final Logger log = Logger.getLogger( DeepClonner.class );
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T clone(T orig) {
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream(10000);
			ObjectOutputStream out = new ObjectOutputStream(bos);
			out.writeObject( orig );
			out.flush();
			out.close();

			ObjectInputStream in = new ObjectInputStream(
					new ByteArrayInputStream( bos.toByteArray() ) );
			
			T clone = (T) in.readObject();

			in.close();
			bos.close();

			return clone;
		} catch ( Throwable e ) {
			log.error( e.getMessage(), e );
            throw new IllegalStateException( e.getMessage(), e );
		}
	}

}
