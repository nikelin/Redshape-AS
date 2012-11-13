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

package org.hibernate.shards.stat;

import org.hibernate.Session;
import org.hibernate.engine.CollectionKey;
import org.hibernate.engine.EntityKey;
import org.hibernate.shards.Shard;
import org.hibernate.shards.engine.ShardedSessionImplementor;
import org.hibernate.shards.session.OpenSessionEvent;
import org.hibernate.shards.util.Sets;
import org.hibernate.stat.SessionStatistics;

import java.util.Set;

/**
 * Sharded implementation of the SessionStatistics that aggregates the
 * statistics of all underlying individual SessionStatistics.
 *
 * @author Tomislav Nad
 */
public class ShardedSessionStatistics implements SessionStatistics {

  private final Set<SessionStatistics> sessionStatistics;

  public ShardedSessionStatistics(ShardedSessionImplementor session) {
    sessionStatistics = Sets.newHashSet();
    for (Shard s : session.getShards()) {
      if (s.getSession() != null) {
        sessionStatistics.add(s.getSession().getStatistics());
      } else {
        OpenSessionEvent ose = new OpenSessionEvent() {
          public void onOpenSession(Session session) {
            sessionStatistics.add(session.getStatistics());
          }
        };
        s.addOpenSessionEvent(ose);
      }
    }
  }

  public int getEntityCount() {
    int count = 0;
    for (SessionStatistics s : sessionStatistics) {
      count += s.getEntityCount();
    }
    return count;
  }

  public int getCollectionCount() {
    int count = 0;
    for (SessionStatistics s : sessionStatistics) {
      count += s.getCollectionCount();
    }
    return count;
  }

  public Set<EntityKey> getEntityKeys() {
    Set<EntityKey> entityKeys = Sets.newHashSet();
    for (SessionStatistics s : sessionStatistics) {
      @SuppressWarnings("unchecked")
      Set<EntityKey> shardEntityKeys = (Set<EntityKey>)s.getEntityKeys();
      entityKeys.addAll(shardEntityKeys);
    }
    return entityKeys;
  }

  public Set<CollectionKey> getCollectionKeys() {
    Set<CollectionKey> collectionKeys = Sets.newHashSet();
    for (SessionStatistics s : sessionStatistics) {
      @SuppressWarnings("unchecked")
      Set<CollectionKey> shardCollectionKeys = (Set<CollectionKey>)s.getCollectionKeys();
      collectionKeys.addAll(shardCollectionKeys);
    }
    return collectionKeys;
  }
}
