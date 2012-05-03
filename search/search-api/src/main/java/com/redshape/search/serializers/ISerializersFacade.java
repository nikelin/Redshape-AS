package com.redshape.search.serializers;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.search.serializers
 * @date 9/5/11 2:25 PM
 */
public interface ISerializersFacade {

	public <T extends ISerializer> T getSerializer( Class<T> serializerClazz );

}
