package com.redshape.search.serializers.test;

import com.redshape.search.serializers.ArraySerializer;
import com.redshape.search.serializers.SerializerException;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertArrayEquals;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.search.serializers
 * @date 10/28/11 9:28 PM
 */
public class ArraySerializerTest {
	private Random random = new Random();



	@Test
	public void testSerializeString() throws SerializerException {
		ArraySerializer serializer = new ArraySerializer();

		String[] subject = new String[] { "3.0", "abc"  };

		assertArrayEquals(
			subject,
			serializer.unserialize(
				serializer.serializeString(subject)
			)
		);
	}

	@Test
	public void testSerializeBytes() throws SerializerException {
		ArraySerializer serializer = new ArraySerializer();

		String[] subject = new String[] { "2.0", "3.0", "4.0" };

		assertArrayEquals(
			subject,
			serializer.unserialize(serializer.serializeBytes(subject))
		);
	}

}
