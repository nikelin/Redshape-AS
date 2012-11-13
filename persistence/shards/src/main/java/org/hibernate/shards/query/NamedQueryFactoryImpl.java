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

import org.hibernate.Query;
import org.hibernate.Session;

/**
 * Facilitates use of named queries when using ShardedQuery.
 *
 * @author maxr@google.com (Max Ross)
 */
public class NamedQueryFactoryImpl implements QueryFactory {

  private final String queryName;

  public NamedQueryFactoryImpl(String queryName) {
    this.queryName = queryName;
  }

  public Query createQuery(Session session) {
    return session.getNamedQuery(queryName);
  }
}
