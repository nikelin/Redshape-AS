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
 * Event that allows the firstResult of a {@link Criteria} to be set lazily.
 * @see Criteria#setFirstResult(int)
 *
 * @author maxr@google.com (Max Ross)
 */
class SetFirstResultEvent implements CriteriaEvent {

  // the firstResult that we'll set on the Criteria when the event fires
  private final int firstResult;

  /**
   * Construct a SetFirstResultEvent
   *
   * @param firstResult the firstResult that
   * we'll set on the {@link Criteria} when the event fires.
   */
  public SetFirstResultEvent(int firstResult) {
    this.firstResult = firstResult;
  }

  public void onEvent(Criteria crit) {
    crit.setFirstResult(firstResult);
  }
}
