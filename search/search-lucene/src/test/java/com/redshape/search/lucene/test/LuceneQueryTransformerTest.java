package com.redshape.search.lucene.test;

import com.redshape.search.query.impl.SearchTermBuilder;
import com.redshape.search.query.terms.ISearchTerm;
import com.redshape.search.query.terms.Operation;
import com.redshape.search.lucene.query.transformers.LuceneQueryTransformer;
import com.redshape.search.query.transformers.QueryTransformationException;
import org.apache.lucene.search.Query;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.search.serializers
 * @date 10/29/11 12:12 AM
 */
public class LuceneQueryTransformerTest {

	@Test
	public void testMain() {
		SearchTermBuilder builder = new SearchTermBuilder();
		ISearchTerm term = builder.or(
			builder.and(
				builder.not(
					builder.field("name", builder.literal("x") )
				),
				builder.field("name2", builder.literal("x2") )
			),
			builder.group( Operation.NOT,
					builder.field("name3", builder.literal("3") ),
					builder.field("name3", builder.literal("4") ) )
		);

		LuceneQueryTransformer transformer = new LuceneQueryTransformer();

		try {
			Query query = transformer.transform( term );
			assertEquals(
				"-name:x +name2:x2 (name3:3 -name3:4)",
				query.toString()
			);
		} catch ( QueryTransformationException e ) {
			e.printStackTrace();
		}
	}

}
