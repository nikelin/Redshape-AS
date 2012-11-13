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

package com.redshape.persistence.shard;

import com.redshape.utils.Commons;
import org.hibernate.ejb.Ejb3Configuration;
import org.hibernate.ejb.HibernatePersistence;
import org.hibernate.shards.cfg.ShardConfiguration;
import org.hibernate.shards.strategy.ShardStrategyFactory;

import javax.persistence.EntityManagerFactory;
import javax.persistence.spi.PersistenceUnitInfo;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.persistence.shard
 * @date 1/27/12 {4:44 PM}
 */
public class ShardedHibernatePersistence extends HibernatePersistence {
    private List<ShardConfiguration> shardConfigurations;
    private ShardStrategyFactory shardStrategyFactory;
    private Map<Integer, Integer> shardIdsMap;

    public ShardedHibernatePersistence(List<ShardConfiguration> shardConfigurations, ShardStrategyFactory shardStrategyFactory) {
        this(shardConfigurations, shardStrategyFactory, new HashMap<Integer, Integer>() );
    }

    public ShardedHibernatePersistence( List<ShardConfiguration> shardConfigurations,
                                        ShardStrategyFactory shardStrategyFactory,
                                        Map<Integer, Integer> shardIdsMap ) {
        Commons.checkNotNull(shardConfigurations);
        Commons.checkNotNull(shardStrategyFactory);
        Commons.checkNotNull(shardIdsMap);

        this.shardConfigurations = shardConfigurations;
        this.shardStrategyFactory = shardStrategyFactory;
        this.shardIdsMap = shardIdsMap;
    }
    
    protected Ejb3Configuration createConfiguration() {
        return new ShardedEjb3Configuration( this.shardConfigurations, this.shardStrategyFactory, this.shardIdsMap);
    }

    @Override
    public EntityManagerFactory createEntityManagerFactory(String persistenceUnitName, Map properties) {
        Ejb3Configuration cfg = this.createConfiguration();
        Ejb3Configuration configured = cfg.configure( persistenceUnitName, properties );
        return configured != null ? configured.buildEntityManagerFactory() : null;
    }

    @Override
    public EntityManagerFactory createContainerEntityManagerFactory(PersistenceUnitInfo info, Map properties) {
        Ejb3Configuration cfg = this.createConfiguration();
        Ejb3Configuration configured = cfg.configure( info, properties );
        return configured != null ? configured.buildEntityManagerFactory() : null;
    }

    @Override
    public EntityManagerFactory createEntityManagerFactory(Map properties) {
        // This is used directly by JBoss so don't remove until further notice.  bill@jboss.org
        Ejb3Configuration cfg = this.createConfiguration();
        return cfg.createEntityManagerFactory( properties );
    }
}
