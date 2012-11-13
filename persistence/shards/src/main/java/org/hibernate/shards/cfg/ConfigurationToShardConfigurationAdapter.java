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

import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

/**
 * Adapt a {@link Configuration} to the {@link org.hibernate.shards.cfg.ShardConfiguration} interface.
 *
 * @author maxr@google.com (Max Ross)
 */
public class ConfigurationToShardConfigurationAdapter implements ShardConfiguration {

  private final Configuration config;

  public ConfigurationToShardConfigurationAdapter(Configuration config) {
    this.config = config;
  }

  public String getShardUrl() {
    return config.getProperty(Environment.URL);
  }

  public String getShardUser() {
    return config.getProperty(Environment.USER);
  }

  public String getShardPassword() {
    return config.getProperty(Environment.PASS);
  }

  public String getShardSessionFactoryName() {
    return config.getProperty(Environment.SESSION_FACTORY_NAME);
  }

  public Integer getShardId() {
    return Integer.parseInt(config.getProperty(ShardedEnvironment.SHARD_ID_PROPERTY));
  }

  public String getShardDatasource() {
    return config.getProperty(Environment.DATASOURCE);
  }

  public String getShardCacheRegionPrefix() {
    return config.getProperty(Environment.CACHE_REGION_PREFIX);
  }
}
