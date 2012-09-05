package com.redshape.forker;

import com.redshape.utils.Commons;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.forker
 * @inspiredBy Jukka Zitting <jukkaz.wordpress.com>
 * @date 1/31/12 {6:02 PM}
 */
public abstract class AbstractForkCommandResponse implements IForkCommandResponse {
    
    private Long id;
    private Status status;
    private Long qualifier;

    protected AbstractForkCommandResponse(Long id, Status status) {
        Commons.checkNotNull(id);

        this.id = id;
        this.status = status;
    }

    @Override
    public Long getQualifier() {
        return qualifier;
    }

    @Override
    public void setQualifier(Long qualifier) {
        this.qualifier = qualifier;
    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public Status getStatus() {
        return this.status;
    }
}
