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

package org.hibernate.shards.session;

import org.hibernate.shards.Shard;
import org.hibernate.shards.ShardId;

import java.util.List;

/**
 * Interface for objects that are able to resolve shard of objects.
 *
 * @author maxr@google.com (Max Ross)
 */
interface ShardIdResolver {

  /**
   * Gets ShardId of the shard given object lives on. Only consideres given
   * Shards.
   *
   * @param obj Object whose Shard should be resolved
   * @param shardsToConsider Shards which should be considered during resolution
   * @return ShardId of the shard the object lives on; null if shard could not be resolved
   */
  /*@Nullable*/ ShardId getShardIdForObject(Object obj, List<Shard> shardsToConsider);

  /**
   * Gets ShardId of the shard given object lives on.
   *
   * @param obj Object whose Shard should be resolved
   * @return ShardId of the shard the object lives on; null if shard could not be resolved
   */
  /*@Nullable*/ ShardId getShardIdForObject(Object obj);
}
