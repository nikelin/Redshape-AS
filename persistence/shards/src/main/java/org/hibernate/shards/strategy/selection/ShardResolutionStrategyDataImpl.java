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

package org.hibernate.shards.strategy.selection;

import java.io.Serializable;

/**
 * @author maxr@google.com (Max Ross)
 */
public class ShardResolutionStrategyDataImpl implements ShardResolutionStrategyData {
  private final String entityName;
  private final Serializable id;

  public ShardResolutionStrategyDataImpl(Class<?> clazz, Serializable id) {
    this(clazz.getName(), id);
  }

  public ShardResolutionStrategyDataImpl(String entityName, Serializable id) {
    this.entityName = entityName;
    this.id = id;
  }

  public String getEntityName() {
    return entityName;
  }

  public Serializable getId() {
    return id;
  }

}
