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
import org.hibernate.shards.util.Preconditions;

/**
 * Threadsafe ExitStrategy implementation that only accepts the first result
 * added.
 *
 * @author maxr@google.com (Max Ross)
 */
public class FirstNonNullResultExitStrategy<T> implements ExitStrategy<T> {

  private T nonNullResult;
  private Shard shard;

  /**
   * Synchronized method guarantees that only the first thread to add a result
   * will have its result reflected.
   */
  public final synchronized boolean addResult(T result, Shard shard) {
    Preconditions.checkNotNull(shard);
    if(result != null && nonNullResult == null) {
      nonNullResult = result;
      this.shard = shard;
      return true;
    }
    return false;
  }

  public T compileResults(ExitOperationsCollector exitOperationsCollector) {
    return nonNullResult;
  }

  public Shard getShardOfResult() {
    return shard;
  }
}
