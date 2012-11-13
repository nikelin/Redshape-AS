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
 * Event that allows the cache region of a {@link Criteria} to be set lazily.
 * @see Criteria#setCacheRegion(String)
 *
 * @author maxr@google.com (Max Ross)
 */
class SetCacheRegionEvent implements CriteriaEvent {

  // the cache region that we'll set on the Criteria when the event fires
  private final String cacheRegion;

  /**
   * Construct a CacheRegionEvent
   *
   * @param cacheRegion the cache region we'll set on the {@link Criteria}
   * when the event fires.
   */
  public SetCacheRegionEvent(String cacheRegion) {
    this.cacheRegion = cacheRegion;
  }

  public void onEvent(Criteria crit) {
    crit.setCacheRegion(cacheRegion);
  }
}
