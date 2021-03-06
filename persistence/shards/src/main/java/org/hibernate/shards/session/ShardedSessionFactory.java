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
import org.hibernate.Interceptor;
import org.hibernate.SessionFactory;
import org.hibernate.shards.ShardId;
import org.hibernate.shards.strategy.ShardStrategyFactory;

import java.util.List;

/**
 * Shard-aware extension to {@link SessionFactory}.  Similar to {@link SessionFactory},
 * ShardedSessionFactory is threadsafe.
 *
 * @author maxr@google.com (Max Ross)
 */
public interface ShardedSessionFactory extends SessionFactory {

  /**
   * @return All an unmodifiable list of the {@link SessionFactory} objects contained within.
   */
  List<SessionFactory> getSessionFactories();

  /**
   * This method is provided to allow a client to work on a subset of
   * org.hibernate.shards or a specialized {@link ShardStrategyFactory}.  By providing
   * the desired shardIds, the client can limit operations to these org.hibernate.shards.
   * Alternatively, this method can be used to create a ShardedSessionFactory
   * with different strategies that might be appropriate for a specific operation.
   *
   * The factory returned will not be stored as one of the factories that would
   * be returned by a call to getSessionFactories.
   *
   * @param shardIds
   * @param shardStrategyFactory
   * @return specially configured ShardedSessionFactory
   */
  ShardedSessionFactory getSessionFactory(List<ShardId> shardIds,
      ShardStrategyFactory shardStrategyFactory);

/**
 * Create database connection(s) and open a <tt>ShardedSession</tt> on it,
 * specifying an interceptor.
 *
 * @param interceptor a session-scoped interceptor
 * @return ShardedSession
 * @throws org.hibernate.HibernateException
 */
 ShardedSession openSession(Interceptor interceptor) throws HibernateException;

  /**
   * Create database connection(s) and open a <tt>ShardedSession</tt> on it.
   *
   * @return ShardedSession
   * @throws org.hibernate.HibernateException
   */
  public ShardedSession openSession() throws HibernateException;
}
