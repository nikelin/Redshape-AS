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

package org.hibernate.shards;

import org.hibernate.shards.util.Preconditions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Base implementation for HasShadIdList.
 * Takes care of null/empty checks.
 *
 * @author maxr@google.com (Max Ross)
 */
public abstract class BaseHasShardIdList implements HasShardIdList {

  // our list of {@link ShardId} objects
  protected final List<ShardId> shardIds;

  /**
   * Construct a BaseHasShardIdList.  {@link List} cannot be empty
   * @param shardIds  the {@link ShardId}s
   */
  protected BaseHasShardIdList(List<ShardId> shardIds) {
    Preconditions.checkNotNull(shardIds);
    Preconditions.checkArgument(!shardIds.isEmpty());
    // make our own copy to be safe
    this.shardIds = new ArrayList<ShardId>(shardIds);
  }

  public List<ShardId> getShardIds() {
    return Collections.unmodifiableList(shardIds);
  }

}
