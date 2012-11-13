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

import org.hibernate.shards.Shard;
import org.hibernate.shards.session.ShardedSession;

import java.util.List;

/**
 * Defines the internal contract between the <tt>ShardedSession</tt> and other
 * parts of Hibernate Shards.
 *
 * @see ShardedSession the interface to the application
 * @see org.hibernate.shards.session.ShardedSessionImpl the actual implementation
 *
 * @author Tomislav Nad
 */
public interface ShardedSessionImplementor {

  /**
   * Gets all the org.hibernate.shards the ShardedSession is spanning.
   *
   * @return list of all org.hibernate.shards the ShardedSession is associated with
   */
  List<Shard> getShards();

}
