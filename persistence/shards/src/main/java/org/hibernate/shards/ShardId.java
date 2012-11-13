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

/**
 * Uniquely identifies a virtual shard.
 *
 * @author maxr@google.com (Max Ross)
 */
public class ShardId {

  private final int shardId;

  public ShardId(int shardId) {
    this.shardId = shardId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    final ShardId shardId1 = (ShardId)o;

    return shardId == shardId1.shardId;
  }

  @Override
  public int hashCode() {
    return shardId;
  }

  public int getId() {
    return shardId;
  }

  @Override
  public String toString() {
    return Integer.toString(shardId);
  }
}
