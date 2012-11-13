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

import org.hibernate.HibernateException;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.shards.ShardId;
import org.hibernate.shards.strategy.ShardStrategyFactory;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This class extends ShardedSessionFactoryImpl and is constructed by supplying
 * a subset of shardIds that are primarily owned by a ShardedSessionFactoryImpl.
 * The purpose of this class is to override the .close() method in order to
 * prevent the SubsetShardedSessionFactoryImpl from closing any session
 * factories that belong to a ShardedSessionFactoryImpl.
 *
 * @author Maulik Shah@google.com (Maulik Shah)
 */
public class SubsetShardedSessionFactoryImpl extends ShardedSessionFactoryImpl {

  public SubsetShardedSessionFactoryImpl(List<ShardId> shardIds,
      Map<SessionFactoryImplementor, Set<ShardId>> sessionFactoryShardIdMap,
      ShardStrategyFactory shardStrategyFactory,
      Set<Class<?>> classesWithoutTopLevelSaveSupport,
      boolean checkAllAssociatedObjectsForDifferentShards) {
    super(shardIds, sessionFactoryShardIdMap, shardStrategyFactory,
        classesWithoutTopLevelSaveSupport,
        checkAllAssociatedObjectsForDifferentShards);
  }

  protected SubsetShardedSessionFactoryImpl(
      Map<SessionFactoryImplementor, Set<ShardId>> sessionFactoryShardIdMap,
      ShardStrategyFactory shardStrategyFactory,
      Set<Class<?>> classesWithoutTopLevelSaveSupport,
      boolean checkAllAssociatedObjectsForDifferentShards) {
    super(sessionFactoryShardIdMap, shardStrategyFactory,
        classesWithoutTopLevelSaveSupport,
        checkAllAssociatedObjectsForDifferentShards);
  }

  /**
   * This method is a NO-OP. As a ShardedSessionFactoryImpl that represents
   * a subset of the application's org.hibernate.shards, it will not close any shard's
   * sessionFactory.
   *
   * @throws HibernateException
   */
  @Override
  public void close() throws HibernateException {
    // no-op: this class should never close session factories
  }

}
