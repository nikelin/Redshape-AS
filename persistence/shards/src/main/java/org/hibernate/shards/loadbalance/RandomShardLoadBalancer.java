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

package org.hibernate.shards.loadbalance;

import org.hibernate.shards.ShardId;

import java.util.List;
import java.util.Random;

/**
 * Random selection load balancing algorithm.
 *
 * @author maxr@google.com (Max Ross)
 */
public class RandomShardLoadBalancer extends BaseShardLoadBalancer {

  private final Random rand = new Random(System.currentTimeMillis());

  /**
   * Construct a RandomShardLoadBalancer
   * @param shardIds the ShardIds that we're balancing across
   */
  public RandomShardLoadBalancer(List<ShardId> shardIds) {
    super(shardIds);
  }

  @Override
  protected int getNextIndex() {
    // Implementation of nextInt() seems to indicate that this method is
    // threadsafe.  I sure hope I'm right about that.
    return rand.nextInt();
  }
}
