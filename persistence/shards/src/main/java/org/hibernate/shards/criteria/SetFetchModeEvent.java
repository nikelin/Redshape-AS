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
import org.hibernate.FetchMode;

/**
 * Event that allows the {@link FetchMode} of a {@link Criteria} to be set lazily.
 * @see Criteria#setFetchMode(String, FetchMode)
 *
 * @author maxr@google.com (Max Ross)
 */
class SetFetchModeEvent implements CriteriaEvent {

  // the association path that will be set on the Criteria
  private final String associationPath;

  // the FetchMode that will be set on the Criteria
  private final FetchMode mode;

  /**
   * Construct a SetFetchModeEvent
   *
   * @param associationPath the association path of the fetch mode
   * we'll set on the {@link Criteria} when the event fires.
   * @param mode the mode we'll set on the {@link Criteria} when the event fires.
   */
  public SetFetchModeEvent(String associationPath, FetchMode mode) {
    this.associationPath = associationPath;
    this.mode = mode;
  }

  public void onEvent(Criteria crit) {
    crit.setFetchMode(associationPath, mode);
  }
}
