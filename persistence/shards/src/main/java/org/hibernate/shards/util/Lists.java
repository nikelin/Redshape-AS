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

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Helper methods related to {@link List}s.
 *
 * @author maxr@google.com (Max Ross)
 */
public class Lists {

  private Lists() { }

  /**
   * Construct a new {@link ArrayList}, taking advantage of type inference to
   * avoid specifying the type on the rhs.
   */
  public static <E> ArrayList<E> newArrayList() {
    return new ArrayList<E>();
  }

  /**
   * Construct a new {@link ArrayList} with the specified capacity, taking advantage of type inference to
   * avoid specifying the type on the rhs.
   */
  public static <E> ArrayList<E> newArrayListWithCapacity(int initialCapacity) {
    return new ArrayList<E>(initialCapacity);
  }

  /**
   * Construct a new {@link ArrayList} with the provided elements, taking advantage of type inference to
   * avoid specifying the type on the rhs.
   */
  public static <E> ArrayList<E> newArrayList(E... elements) {
    ArrayList<E> set = newArrayList();
    Collections.addAll(set, elements);
    return set;
  }

  /**
   * Construct a new {@link ArrayList} with the contents of the provided {@link Iterable}, taking advantage of type inference to
   * avoid specifying the type on the rhs.
   */
  public static <E> ArrayList<E> newArrayList(Iterable<? extends E> elements) {
    ArrayList<E> list = newArrayList();
    for(E e : elements) {
      list.add(e);
    }
    return list;
  }

  /**
   * Construct a new {@link LinkedList}, taking advantage of type inference to
   * avoid specifying the type on the rhs.
   */
  public static <E> LinkedList<E> newLinkedList() {
    return new LinkedList<E>();
  }
}
