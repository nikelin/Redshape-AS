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
