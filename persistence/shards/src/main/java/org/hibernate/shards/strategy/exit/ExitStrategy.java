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

import org.hibernate.shards.Shard;

/**
 * Classes implementing this interface gather results from operations that are
 * executed across org.hibernate.shards.  If you intend to use a specific implementation
 * in conjunction with ParallelShardAccessStrategy that implementation must
 * be threadsafe.
 *
 * @author maxr@google.com (Max Ross)
 */
public interface ExitStrategy<T> {

  /**
   * Add the provided result and return whether or not the caller can halt
   * processing.
   * @param result The result to add
   * @return Whether or not the caller can halt processing
   */
  boolean addResult(T result, Shard shard);

  T compileResults(ExitOperationsCollector exitOperationsCollector);
}
