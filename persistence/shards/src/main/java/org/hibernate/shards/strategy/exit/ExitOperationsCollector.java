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

import org.hibernate.engine.SessionFactoryImplementor;

import java.util.List;

/**
 * Classes that implement this interface are designed to manage the results
 * of a incomplete execution of a query/critieria. For example, with averages
 * the result of each query/critieria should be a list objects on which to
 * calculate the average, rather than the avgerages on each shard. Or the
 * the sum of maxResults(200) should be the sum of only 200 results, not the
 * sum of the sums of 200 results per shard.
 *
 * @author Maulik Shah
 */
public interface ExitOperationsCollector {

  List<Object> apply(List<Object> result);

  void setSessionFactory(SessionFactoryImplementor sessionFactoryImplementor);

}
