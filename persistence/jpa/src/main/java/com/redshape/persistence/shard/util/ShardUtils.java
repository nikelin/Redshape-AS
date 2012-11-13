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

package com.redshape.persistence.shard.util;

import org.hibernate.shards.ShardId;
import org.hibernate.shards.cfg.ShardConfiguration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.persistence.shard.util
 * @date 1/27/12 {7:33 PM}
 */
public final class ShardUtils {
    
    public static class ShardConfigurationImpl implements ShardConfiguration {
        private String shardUrl;
        private String shardUser;
        private String shardPassword;
        private String shardSessionFactoryName;
        private Integer shardId;
        private String shardDataSource;
        private String shardCacheRegionPrefix;

        public ShardConfigurationImpl(String shardUrl,
                                      String shardUser,
                                      String shardPassword,
                                      String shardSessionFactoryName,
                                      Integer shardId,
                                      String shardDataSource,
                                      String shardCacheRegionPrefix) {
            this.shardUrl = shardUrl;
            this.shardUser = shardUser;
            this.shardPassword = shardPassword;
            this.shardSessionFactoryName = shardSessionFactoryName;
            this.shardId = shardId;
            this.shardDataSource = shardDataSource;
            this.shardCacheRegionPrefix = shardCacheRegionPrefix;
        }

        public String getShardUrl() {
            return shardUrl;
        }

        public String getShardUser() {
            return shardUser;
        }

        public String getShardPassword() {
            return shardPassword;
        }

        public String getShardSessionFactoryName() {
            return shardSessionFactoryName;
        }

        public Integer getShardId() {
            return shardId;
        }

        public String getShardDatasource() {
            return shardDataSource;
        }

        public String getShardCacheRegionPrefix() {
            return shardCacheRegionPrefix;
        }
    }
    
    public static ShardConfiguration createConfiguration(String shardUrl,
                                                         String shardUser,
                                                         String shardPassword,
                                                         String shardSessionFactoryName,
                                                         Integer shardId,
                                                         String shardDataSource,
                                                         String shardCacheRegionPrefix) {
        return new ShardConfigurationImpl(shardUrl,
                                          shardUser,
                                          shardPassword,
                                          shardSessionFactoryName,
                                          shardId,
                                          shardDataSource,
                                          shardCacheRegionPrefix );
    }

    public List<ShardId> shardsIds( Collection<ShardConfiguration> configurations ) {
        return ShardUtils.shardsToIds(configurations);
    }
    
    public static List<ShardId> shardsToIds( Collection<ShardConfiguration> configurations ) {
       List<ShardId> ids = new ArrayList<ShardId>();
        for ( ShardConfiguration configuration : configurations ) {
            ids.add( new ShardId( configuration.getShardId() ) );
        }

        return ids;
    }
    
}
