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
import org.hibernate.criterion.Criterion;

/**
 * Event that allows a Criterion to be lazily added to a Criteria.
 * @see Criteria#add(Criterion) 
 *
 * @author maxr@google.com (Max Ross)
 */
class AddCriterionEvent implements CriteriaEvent {

  // the Criterion we're going to add when the event fires
  private final Criterion criterion;

  public AddCriterionEvent(Criterion criterion) {
    this.criterion = criterion;
  }


  public void onEvent(Criteria crit) {
    crit.add(criterion);
  }
}
