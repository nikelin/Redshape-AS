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
import org.hibernate.transform.ResultTransformer;

/**
 * Event that allows the {@link ResultTransformer} of a {@link Criteria} to be set lazily.
 * @see Criteria#setResultTransformer(ResultTransformer)
 *
 * @author maxr@google.com (Max Ross)
 */
class SetResultTransformerEvent implements CriteriaEvent {

  // the resultTransformer we'll set on the Critieria when the event fires
  private final ResultTransformer resultTransformer;

  /**
   * Constructs a SetResultTransformerEvent
   *
   * @param resultTransformer the resultTransformer we'll set on the {@link Criteria} when
   * the event fires.
   */
  public SetResultTransformerEvent(ResultTransformer resultTransformer) {
    this.resultTransformer = resultTransformer;
  }

  public void onEvent(Criteria crit) {
    crit.setResultTransformer(resultTransformer);
  }
}
