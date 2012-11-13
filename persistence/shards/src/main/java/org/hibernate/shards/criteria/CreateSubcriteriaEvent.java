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
 * Event that allows a Subcriteria to be lazily added to a Criteria.
 *
 * @author maxr@google.com (Max Ross)
 */
public class CreateSubcriteriaEvent implements CriteriaEvent {

  private final SubcriteriaFactory subcriteriaFactory;
  private final ShardedSubcriteriaImpl.SubcriteriaRegistrar subcriteriaRegistrar;

  public CreateSubcriteriaEvent(SubcriteriaFactory subcriteriaFactory, ShardedSubcriteriaImpl.SubcriteriaRegistrar subcriteriaRegistrar) {
    this.subcriteriaFactory = subcriteriaFactory;
    this.subcriteriaRegistrar = subcriteriaRegistrar;
  }

  public void onEvent(Criteria crit) {
    subcriteriaRegistrar.establishSubcriteria(crit, subcriteriaFactory);
  }
}
