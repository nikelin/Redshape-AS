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
import org.hibernate.shards.util.Lists;

import java.util.List;

/**
 * Threadsafe ExistStrategy that concatenates all the lists that are added.
 *
 * @author maxr@google.com (Max Ross)
 */
public class ConcatenateListsExitStrategy implements ExitStrategy<List<Object>> {

  private final List<Object> result = Lists.newArrayList();

  public synchronized boolean addResult(List<Object> oneResult, Shard shard) {
    result.addAll(oneResult);
    return false;
  }

  public List<Object> compileResults(ExitOperationsCollector exitOperationsCollector) {
    return exitOperationsCollector.apply(result);
  }
}
