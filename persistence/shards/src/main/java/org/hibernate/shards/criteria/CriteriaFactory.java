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

package org.hibernate.shards.criteria;

import org.hibernate.Criteria;
import org.hibernate.Session;

/**
 * Factory that knows how to create a {@link Criteria} for a given {@link Session}
 *
 * @author maxr@google.com (Max Ross)
 */
public interface CriteriaFactory {

  /**
   * Create a {@link Criteria} for the given {@link Session}
   *
   * @param session the {@link Session}  to be used when creating the {@link Criteria}
   * @return a {@link Criteria} associated with the given {@link Session}
   */
  Criteria createCriteria(Session session);
}
