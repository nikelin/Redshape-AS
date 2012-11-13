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

import org.hibernate.shards.Shard;
import org.hibernate.shards.util.Lists;
import org.hibernate.shards.util.Maps;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Maulik Shah
 */
public class QueryResult {

  private final Map<Shard, List> resultMap = Maps.newHashMap();

  private final List<Object> entityList = Lists.newArrayList();

  public Map<Shard, List> getResultMap() {
    return Collections.unmodifiableMap(resultMap);
  }

  public void add(Shard shard, List<Object> list) {
    resultMap.put(shard, list);
    entityList.addAll(list);
  }

  public void add(QueryResult result) {
    resultMap.putAll(result.getResultMap());
    entityList.addAll(result.getEntityList());
  }

  public List<Object> getEntityList() {
    return entityList;
  }

}
