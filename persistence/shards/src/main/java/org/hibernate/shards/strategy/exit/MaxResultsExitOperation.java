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

import java.util.List;

/**
 * @author Maulik Shah
 */
public class MaxResultsExitOperation implements ExitOperation {

  private final int maxResults;

  public MaxResultsExitOperation(int maxResults) {
    this.maxResults = maxResults;
  }

  public List<Object> apply(List<Object> results) {
    List<Object> nonNullResults = ExitOperationUtils.getNonNullList(results);
    return nonNullResults.subList(0, Math.min(nonNullResults.size(), maxResults));
  }
}
