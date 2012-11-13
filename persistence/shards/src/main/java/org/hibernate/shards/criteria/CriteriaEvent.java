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

/**
 * Interface for events that can be laziliy applied to a
 * {@link org.hibernate.Criteria}. Useful because we don't allocate a
 * {@link org.hibernate.Criteria} until we actually need it, and programmers
 * might be calling a variety of methods against the
 * {@link org.hibernate.shards.criteria.ShardedCriteria}
 * which need to be applied to the actual {@link org.hibernate.Criteria} once
 * the actual {@link org.hibernate.Criteria} when it is allocated.
 *
 * @author maxr@google.com (Max Ross)
 */
public interface CriteriaEvent {

  /**
   * Apply the event
   * @param crit the Criteria to apply the event to
   */
  void onEvent(Criteria crit);
}
