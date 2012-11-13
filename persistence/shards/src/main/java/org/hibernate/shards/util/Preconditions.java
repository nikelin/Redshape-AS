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
 * Helper methods for checking preconditions.
 *
 * @author maxr@google.com (Max Ross)
 */
public class Preconditions {

  private Preconditions() { }

  /**
   * @param expression the boolean to evaluate
   * @throws IllegalArgumentException thrown if boolean is false
   */
  public static void checkArgument(boolean expression) {
    if (!expression) {
      throw new IllegalArgumentException();
    }
  }

  /**
   * @param expression the boolean to evaluate
   * @throws IllegalStateException thrown if boolean is false
   */
  public static void checkState(boolean expression) {
    if (!expression) {
      throw new IllegalStateException();
    }
  }

  /**
   * @param reference the object to compare against null
   * @throws NullPointerException thrown if object is null
   */
  public static void checkNotNull(Object reference) {
    if (reference == null) {
      throw new NullPointerException();
    }
  }
}
