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

/**
 * A simple class to represent a pair.
 *
 * @author maxr@google.com (Max Ross)
 */
public class Pair<A,B> {
  public final A first;

  /** The second element of the pair. */
  public final B second;

  private Pair(/*@Nullable*/ A first, /*@Nullable*/ B second) {
    this.first = first;
    this.second = second;
  }

  public A getFirst() {
    return first;
  }

  public B getSecond() {
    return second;
  }

  public static <A,B> Pair<A,B> of(/*@Nullable*/ A first, /*@Nullable*/ B second) {
    return new Pair<A,B>(first, second);
  }

  private static boolean eq(/*@Nullable*/ Object a, /*@Nullable*/ Object b) {
    return a == b || (a != null && a.equals(b));
  }

  @Override
  public boolean equals(/*@Nullable*/ Object object) {
    if (object instanceof Pair<?,?>) {
      Pair<?,?> other = (Pair<?,?>) object;
      return eq(first, other.first) && eq(second, other.second);
    }
    return false;
  }
}
