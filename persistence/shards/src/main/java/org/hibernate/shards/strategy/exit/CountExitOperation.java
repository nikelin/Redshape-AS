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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.CountProjection;
import org.hibernate.criterion.Projection;
import org.hibernate.shards.util.Preconditions;

import java.util.List;

/**
 * @author Maulik Shah
 */
public class CountExitOperation implements ProjectionExitOperation {

  private final boolean distinct;

  private final Log log = LogFactory.getLog(getClass());

  public CountExitOperation(Projection projection) {
    Preconditions.checkState(projection instanceof CountProjection);

    distinct = projection.toString().indexOf("distinct") != -1;

    /**
     * TODO(maulik) we need to figure out how to work with distinct
     * the CountProjection will return a count that is distinct for a particular
     * shard, however, without knowing which elements it has seen, we cannot
     * aggregate the counts.
     */
    log.error("not ready to use!");
    throw new UnsupportedOperationException();
  }

  public List<Object> apply(List<Object> results) {
    // TODO(maulik) implement this
    log.error("not ready to use!");
    throw new UnsupportedOperationException();
  }
}
