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
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Round robin load balancing algorithm.
 *
 * @author maxr@google.com (Max Ross)
 */
public class RoundRobinShardLoadBalancer extends BaseShardLoadBalancer {

  // Can be shared by multiple threads so access to the counter
  // needs to be threadsafe.
  private final AtomicInteger nextIndex = new AtomicInteger();

  /**
   * Construct a RoundRobinShardLoadBalancer
   * @param shardIds the ShardIds that we're balancing across
   */
  public RoundRobinShardLoadBalancer(List<ShardId> shardIds) {
    super(shardIds);
  }

  @Override
  protected int getNextIndex() {
    return nextIndex.getAndIncrement() % getShardIds().size();
  }
}

