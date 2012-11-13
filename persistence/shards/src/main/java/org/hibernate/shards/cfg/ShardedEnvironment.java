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

package org.hibernate.shards.cfg;

import org.hibernate.cfg.Environment;

/**
 * Hibernate Shards configuration properties.
 * @see Environment
 *
 * @author maxr@google.com (Max Ross)
 */
public final class ShardedEnvironment {

  /**
   * Configuration property that determines whether or not we examine all
   * associated objects for shard conflicts when we save or update.  A shard
   * conflict is when we attempt to associate one object that lives on shard X
   * with an object that lives on shard Y.  Turning this on will hurt
   * performance but will prevent the programmer from ending up with the
   * same entity on multiple org.hibernate.shards, which is bad (at least in the current version).
   */
  public static final String CHECK_ALL_ASSOCIATED_OBJECTS_FOR_DIFFERENT_SHARDS = "hibernate.shard.enable_cross_shard_relationship_checks";

  /**
   * Unique identifier for a shard.  Must be an Integer.
   */
  public static final String SHARD_ID_PROPERTY = "hibernate.connection.shard_id";

  private ShardedEnvironment() {}
}
