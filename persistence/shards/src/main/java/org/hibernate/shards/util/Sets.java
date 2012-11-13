/*
 * Copyright 2012 Cyril A. Karpenko
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.hibernate.shards.util;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Helper methods related to {@link Set}s.
 *
 * @author maxr@google.com (Max Ross)
 */
public class Sets {
  private Sets() {}

  /**
   * Construct a new {@link HashSet}, taking advantage of type inference to
   * avoid specifying the type on the rhs.
   */
  public static <E> HashSet<E> newHashSet() {
    return new HashSet<E>();
  }

  /**
   * Construct a new {@link HashSet} with the provided elements, taking advantage of type inference to
   * avoid specifying the type on the rhs.
   */
  public static <E> HashSet<E> newHashSet(E... elements) {
    HashSet<E> set = newHashSet();
    Collections.addAll(set, elements);
    return set;
  }

  /**
   * Construct a new {@link HashSet} with the contents of the provided {@link Iterable}, taking advantage of type inference to
   * avoid specifying the type on the rhs.
   */
  public static <E> HashSet<E> newHashSet(Iterable<? extends E> elements) {
    HashSet<E> set = newHashSet();
    for(E e : elements) {
      set.add(e);
    }
    return set;
  }
}
