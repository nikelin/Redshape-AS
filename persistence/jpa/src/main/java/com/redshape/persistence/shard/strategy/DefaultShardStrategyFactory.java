package com.redshape.persistence.shard.strategy;

import org.hibernate.shards.ShardId;
import org.hibernate.shards.strategy.ShardStrategy;
import org.hibernate.shards.strategy.ShardStrategyFactory;
import org.hibernate.shards.strategy.ShardStrategyImpl;
import org.hibernate.shards.strategy.access.ShardAccessStrategy;
import org.hibernate.shards.strategy.resolution.ShardResolutionStrategy;
import org.hibernate.shards.strategy.selection.ShardSelectionStrategy;

import java.util.List;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.persistence.shard.strategy
 * @date 1/27/12 {6:54 PM}
 */
public class DefaultShardStrategyFactory implements ShardStrategyFactory {

    private ShardSelectionStrategy selectionStrategy;
    private ShardAccessStrategy accessStrategy;
    private ShardResolutionStrategy resolutionStrategy;

    public DefaultShardStrategyFactory( ShardSelectionStrategy selectionStrategy,
                                        ShardResolutionStrategy resolutionStrategy,
                                        ShardAccessStrategy accessStrategy ) {
        this.selectionStrategy = selectionStrategy;
        this.resolutionStrategy = resolutionStrategy;
        this.accessStrategy = accessStrategy;
    }

    @Override
    public ShardStrategy newShardStrategy(List<ShardId> shardIds) {
        return new ShardStrategyImpl(
            this.selectionStrategy,
            this.resolutionStrategy,
            this.accessStrategy
        );
    }
}
