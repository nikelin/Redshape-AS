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
 * Event that allows the cacheability of a {@link Criteria} to be set lazily.
 * @see Criteria#setCacheable(boolean)
 *
 * @author maxr@google.com (Max Ross)
 */
class SetCacheableEvent implements CriteriaEvent {

  // the value to which we're going to set the cacheability of the Criteria
  // when the event fires
  private boolean cacheable;

  /**
   * Construct a SetCacheableEvent
   *
   * @param cacheable the value to which we'll set the cacheability when the event
   * fires
   */
  public SetCacheableEvent(boolean cacheable) {
    this.cacheable = cacheable;
  }

  public void onEvent(Criteria crit) {
    crit.setCacheable(cacheable);
  }
}
