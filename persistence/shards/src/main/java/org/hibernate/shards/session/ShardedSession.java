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

import org.hibernate.classic.Session;
import org.hibernate.shards.ShardId;


/**
 * The main runtime inteface between Java application and Hibernate Shards.<br>
 * ShardedSession represents a logical transaction that might be spanning
 * multiple org.hibernate.shards. It follows the contract set by Session API, and adds some
 * shard-related methods.
 *
 * @see org.hibernate.shards.session.ShardedSessionFactory
 * @see Session
 * @author maxr@google.com (Max Ross)
 */
public interface ShardedSession extends Session {

  /**
   * Gets the non-sharded session with which the objects is associated.
   *
   * @param obj  the object for which we want the Session
   * @return the Session with which this object is associated, or null if the
   * object is not associated with a session belonging to this ShardedSession
   */
  Session getSessionForObject(Object obj);

  /**
   * Gets the ShardId of the shard with which the objects is associated.
   *
   * @param obj  the object for which we want the Session
   * @return the ShardId of the Shard with which this object is associated, or
   * null if the object is not associated with a shard belonging to this
   * ShardedSession
   */
  ShardId getShardIdForObject(Object obj);

  /**
   * Place the session into a state where every create operation takes place
   * on the same shard.  Once the shard is locked on a session it cannot
   * be unlocked.
   */
  void lockShard();
}
