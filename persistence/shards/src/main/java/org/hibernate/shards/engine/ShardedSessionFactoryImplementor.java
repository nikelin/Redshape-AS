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

package org.hibernate.shards.engine;

import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.shards.ShardId;
import org.hibernate.shards.session.ShardedSessionFactory;

import java.util.Map;
import java.util.Set;

/**
 * Internal interface for implementors of ShardedSessionFactory
 *
 * @author Tomislav Nad
 */
public interface ShardedSessionFactoryImplementor extends ShardedSessionFactory, SessionFactoryImplementor {

  Map<SessionFactoryImplementor, Set<ShardId>> getSessionFactoryShardIdMap();

  boolean containsFactory(SessionFactoryImplementor factory);
}
