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

package org.hibernate.shards;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.classic.Session;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.shards.criteria.CriteriaEvent;
import org.hibernate.shards.criteria.CriteriaId;
import org.hibernate.shards.criteria.ShardedCriteria;
import org.hibernate.shards.query.QueryEvent;
import org.hibernate.shards.query.QueryId;
import org.hibernate.shards.query.ShardedQuery;
import org.hibernate.shards.session.OpenSessionEvent;

import java.util.List;
import java.util.Set;

/**
 * Interface representing a Shard.  A shard is a physical partition (as opposed
 * to a virtual partition).  Shards know how to lazily instantiate Sessions
 * and apply {@link OpenSessionEvent}s, {@link CriteriaEvent}s, and {@link QueryEvent}s.
 * Anybody else have a nagging suspicion this can get folded into the Session
 * itself?
 *
 * @author maxr@google.com (Max Ross)
 *         Tomislav Nad
 */
public interface Shard {

  /**
   * @return the SessionFactoryImplementor that owns the Session associated with this Shard
   */
  SessionFactoryImplementor getSessionFactoryImplementor();

  /**
   * @return the Session associated with this Shard.  Will return null if
   * the Session has not yet been established.
   */
  /* @Nullable */ Session getSession();

  /**
   * @param event the event to add
   */
  void addOpenSessionEvent(OpenSessionEvent event);

  /**
   * @return establish a Session using the SessionFactoryImplementor associated
   * with this Shard and apply any OpenSessionEvents that have been added.  If
   * the Session has already been established just return it.
   */
  Session establishSession();

  /**
   * @param id the id of the Criteria
   * @return the Critieria uniquely identified by the given id (unique to the Shard)
   */
  Criteria getCriteriaById(CriteriaId id);

  /**
   * @param id the id of the Criteria with which the event should be associated
   * @param event the event to add
   */
  void addCriteriaEvent(CriteriaId id, CriteriaEvent event);

  /**
   * @param shardedCriteria  the ShardedCriteria for which this Shard should
   * create an actual {@link Criteria} object.
   * @return a Criteria for the given ShardedCriteria
   */
  Criteria establishCriteria(ShardedCriteria shardedCriteria);

  /**
   * @see Criteria#list()
   */
  List<Object> list(CriteriaId criteriaId);

  /**
   * @see Criteria#uniqueResult()
   */
  Object uniqueResult(CriteriaId criteriaId);

  /**
   * @return the ids of the virtual org.hibernate.shards that are mapped to this physical shard.
   * The returned Set is unmodifiable.
   */
  Set<ShardId> getShardIds();

  /**
   * @param queryId the id of the Query
   * @return the Query uniquely identified by the given id (unique to the Shard)
   */
  Query getQueryById(QueryId queryId);

  /**
   * @param id the id of the Query with which the event should be associated
   * @param event the event to add
   */
  void addQueryEvent(QueryId id, QueryEvent event);

  /**
   * @param shardedQuery  the ShardedQuery for which this Shard should
   * create an actual {@link Query} object.
   * @return a Query for the given ShardedQuery
   */
  Query establishQuery(ShardedQuery shardedQuery);

  /**
   * @see Query#list()
   */
  List<Object> list(QueryId queryId);

  /**
   * @see Query#uniqueResult()
   */
  Object uniqueResult(QueryId queryId);
}
