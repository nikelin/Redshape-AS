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

/**
 * Describes the configuration properties that can vary across the {@link org.hibernate.SessionFactory}
 * instances contained within your {@link org.hibernate.shards.session.ShardedSessionFactory}.
 *
 * @author maxr@google.com (Max Ross)
 */
public interface ShardConfiguration {

  /**
   * @see org.hibernate.cfg.Environment#URL
   * @return the url of the shard.
   */
  String getShardUrl();

  /**
   * @see org.hibernate.cfg.Environment#USER
   * @return the user that will be sent to the shard for authentication
   */
  String getShardUser();

  /**
   * @see org.hibernate.cfg.Environment#PASS
   * @return the password that will be sent to the shard for authentication
   */
  String getShardPassword();

  /**
   * @return the name that the {@link org.hibernate.SessionFactory} created from
   * this config will have
   */
  String getShardSessionFactoryName();

  /**
   * @return unique id of the shard
   */
  Integer getShardId();

  /**
   * @see org.hibernate.cfg.Environment#DATASOURCE
   * @return the datasources for the shard
   */
  String getShardDatasource();

  /**
   * @see org.hibernate.cfg.Environment#CACHE_REGION_PREFIX
   * @return the cache region prefix for the shard
   */
  String getShardCacheRegionPrefix();
}
