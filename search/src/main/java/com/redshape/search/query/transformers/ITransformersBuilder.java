package com.redshape.search.query.transformers;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.search.query.transformers
 * @date 9/5/11 3:16 PM
 */
public interface ITransformersBuilder {

 	public <T extends IQueryTransformer> T getTransformer( Class<? extends T> clazz );

}
