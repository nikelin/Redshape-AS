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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.Projection;
import org.hibernate.shards.util.Lists;
import org.hibernate.shards.util.Pair;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Maulik Shah
 */
public class ShardedAvgExitOperation implements ProjectionExitOperation {

  private final Log log = LogFactory.getLog(getClass());

  public ShardedAvgExitOperation(Projection projection) {
    log.error("not ready to use!");
    throw new UnsupportedOperationException();
  }

  public List<Object> apply(List<Object> results) {
    BigDecimal value = new BigDecimal(0.0);
    BigDecimal count = new BigDecimal(0.0);
    @SuppressWarnings("unchecked")
    List<Pair<Double, Integer>> pairList = (List<Pair<Double, Integer>>) (List) results;
    for(Pair<Double, Integer> pair : pairList) {
      // we know the order of the pair (avg, count) by convention of ShardedAvgProjection
      value = value.add(new BigDecimal(pair.first));
      count = count.add(new BigDecimal(pair.second));
    }
    return Lists.newArrayList((Object)value.divide(count));
  }
}
