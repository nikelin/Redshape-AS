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
import org.hibernate.criterion.Projection;

/**
 * Event that allows the {@link Projection} of a {@link Criteria} to be set lazily.
 * @see Criteria#setProjection(Projection)
 *
 * @author maxr@google.com (Max Ross)
 */
class SetProjectionEvent implements CriteriaEvent {

  // the Projection we'll set on the Critiera when the event fires
  private final Projection projection;

  /**
   * Constructs a SetProjectionEvent
   *
   * @param projection the projection we'll set on the {@link Criteria} when the
   * event fires.
   */
  public SetProjectionEvent(Projection projection) {
    this.projection = projection;
  }


  public void onEvent(Criteria crit) {
    crit.setProjection(projection);
  }
}
