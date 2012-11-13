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

/**
 * Interface for events that can be lazily applied to a
 * {@link org.hibernate.Query}. Useful because we don't allocate a
 * {@link org.hibernate.Query} until we actually need it, and programmers
 * might be calling a variety of methods against
 * {@link org.hibernate.shards.query.ShardedQueryImpl}
 * which need to be applied to the actual {@link org.hibernate.Query} when
 * it is allocated.
 *
 * @author Maulik Shah
 */

public interface QueryEvent {

  /**
   * Apply the event
   * @param query the Query to apply the event to
   */
  void onEvent(Query query);
}
