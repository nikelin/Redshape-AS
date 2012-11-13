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
 * String utilities.
 *
 * @author maxr@google.com (Max Ross)
 */
public class StringUtil {

  /**
   * Helper function for null, empty, and whitespace string testing.
   *
   * @return true if s == null or s.equals("") or s contains only whitespace
   *         characters.
   */
  public static boolean isEmptyOrWhitespace(String s) {
    s = makeSafe(s);
    for (int i = 0, n = s.length(); i < n; i++) {
      if (!Character.isWhitespace(s.charAt(i))) {
        return false;
      }
    }
    return true;
  }

  /**
   * @return true if s == null or s.equals("")
   */
  public static boolean isEmpty(String s) {
    return makeSafe(s).length() == 0;
  }

  /**
   * Helper function for making null strings safe for comparisons, etc.
   *
   * @return (s == null) ? "" : s;
   */
  public static String makeSafe(String s) {
    return (s == null) ? "" : s;
  }

  /**
   * @return the string provided with its first character capitalized
   */
  public static String capitalize(String s) {
    if (s.length() == 0) {
      return s;
    }
    char first = s.charAt(0);
    char capitalized = Character.toUpperCase(first);
    return (first == capitalized)
        ? s
        : capitalized + s.substring(1);
  }
}
