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

package org.hibernate.shards.query;

/**
 * @author Maulik Shah
 */
public class QueryId {

  private final int id;

  public QueryId(int id) {
    this.id = id;
  }

  public int getId() {
    return id;
  }

  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof QueryId)) {
      return false;
    }

    final QueryId queryId = (QueryId) o;

    if (id != queryId.id) {
      return false;
    }

    return true;
  }

  public int hashCode() {
    return id;
  }

}
