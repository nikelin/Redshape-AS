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

package org.hibernate.shards.query;

import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.shards.strategy.exit.ExitOperationsCollector;
import org.hibernate.shards.strategy.exit.FirstResultExitOperation;
import org.hibernate.shards.strategy.exit.MaxResultsExitOperation;

import java.util.List;

/**
 * Exit operations for queries is essentially not implemented. Its intended use
 * is to record a set of aggregation type operations to be executed on the
 * combined results for a query executed on each shard.
 *
 * We do implement setMaxResults and setFirstResult as these operations do not
 * require parsing the query string.
 *
 * {@inheritDoc}
 *
 * @author Maulik Shah
 */
public class ExitOperationsQueryCollector implements ExitOperationsCollector {

  // maximum number of results requested by the client
  private Integer maxResults = null;

  // index of the first result requested by the client
  private Integer firstResult = null;

  public List<Object> apply(List<Object> result) {
    if (firstResult != null) {
      result = new FirstResultExitOperation(firstResult).apply(result);
    }
    if (maxResults != null) {
      result = new MaxResultsExitOperation(maxResults).apply(result);
    }

    return result;
  }

  public void setSessionFactory(SessionFactoryImplementor sessionFactoryImplementor) {
    throw new UnsupportedOperationException();
  }

  public ExitOperationsCollector setMaxResults(int maxResults) {
    this.maxResults = maxResults;
    return this;
  }

  public ExitOperationsCollector setFirstResult(int firstResult) {
    this.firstResult = firstResult;
    return this;
  }

}
