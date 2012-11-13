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

package org.hibernate.shards.strategy.exit;

import org.hibernate.criterion.Projection;
import org.hibernate.shards.util.Lists;
import org.hibernate.shards.util.Sets;

import java.util.List;
import java.util.Set;

/**
 * @author Maulik Shah
 */
public class DistinctExitOperation implements ExitOperation {

  private final Projection distinct;

  public DistinctExitOperation(Projection distinct) {
    this.distinct = distinct;
    //TODO (maulik) Make Distinct Work
    throw new UnsupportedOperationException();
  }

  public List<Object> apply(List<Object> results) {
    Set<Object> uniqueSet = Sets.newHashSet();
    uniqueSet.addAll(ExitOperationUtils.getNonNullList(results));

    List<Object> uniqueList = Lists.newArrayList(uniqueSet);
    
    return uniqueList;
  }
}
