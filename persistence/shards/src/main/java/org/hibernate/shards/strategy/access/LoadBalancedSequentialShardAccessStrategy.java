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

import org.hibernate.shards.Shard;
import org.hibernate.shards.util.Iterables;

import java.util.List;
import java.util.Random;

/**
 * A SequentialShardAccessStrategy starts with the first Shard in the list
 * every time.  If the ExitStrategy with which the AccessStrategy is paired
 * supports early exit (keep searching until you have 100 results), the first
 * shard in the list may receive a disproportionately high percentage of the
 * queries.  In order to combat this we have a load balanced approach that
 * adjusts that provides a rotated view of the list of org.hibernate.shards.  The list is
 * rotated by a different amount each time.  The amount by which we rotate
 * is random because doing a true round-robin would require that we know
 * the org.hibernate.shards we're rotating in advance, but the org.hibernate.shards passed to a
 * ShardAccessStrategy can vary between invocations.
 *
 * @author maxr@google.com (Max Ross)
 */
public class LoadBalancedSequentialShardAccessStrategy extends SequentialShardAccessStrategy {

  private final Random rand;

  public LoadBalancedSequentialShardAccessStrategy() {
    this.rand = new Random(System.currentTimeMillis());
  }

  @Override
  protected Iterable<Shard> getNextOrderingOfShards(List<Shard> shards) {
    return Iterables.rotate(shards, rand.nextInt() % shards.size());
  }

}
