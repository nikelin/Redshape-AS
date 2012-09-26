package com.redshape.search.query.transformers;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.search.query.transformers
 * @date 9/5/11 3:25 PM
 */
public class StandardTransformersBuilder implements ITransformersBuilder {
	private Map<Class<? extends IQueryTransformer>, IQueryTransformer> transformers
			= new HashMap<Class<? extends IQueryTransformer>, IQueryTransformer>();

	public StandardTransformersBuilder( Map<Class<? extends IQueryTransformer>,
								 IQueryTransformer> transformers ) {
		this.transformers = transformers;
	}

	@Override
	public <T extends IQueryTransformer> T getTransformer(Class<? extends T> clazz) {
		return (T) transformers.get( clazz );
	}
}
