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

package org.hibernate.shards.strategy.access;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.shards.Shard;
import org.hibernate.shards.ShardOperation;
import org.hibernate.shards.strategy.exit.ExitOperationsCollector;
import org.hibernate.shards.strategy.exit.ExitStrategy;

import java.util.List;

/**
 * @author maxr@google.com (Max Ross)
 */
public class SequentialShardAccessStrategy implements ShardAccessStrategy {

  private final Log log = LogFactory.getLog(getClass());

  public <T> T apply(List<Shard> shards, ShardOperation<T> operation, ExitStrategy<T> exitStrategy, ExitOperationsCollector exitOperationsCollector) {
    for(Shard shard : getNextOrderingOfShards(shards)) {
      if(exitStrategy.addResult(operation.execute(shard), shard)) {
        log.debug(
            String.format(
                "Short-circuiting operation %s after execution against shard %s",
                operation.getOperationName(),
                shard));
        break;
      }
    }
    return exitStrategy.compileResults(exitOperationsCollector);
  }

  /**
   * Override this method if you want to control the order in which the
   * org.hibernate.shards are operated on (this comes in handy when paired with exit
   * strategies that allow early exit because it allows you to evenly
   * distribute load).  Deafult implementation is to just iterate in the
   * same order every time.
   * @param shards The org.hibernate.shards we might want to reorder
   * @return Reordered view of the org.hibernate.shards.
   */
  protected Iterable<Shard> getNextOrderingOfShards(List<Shard> shards) {
    return shards;
  }
}
