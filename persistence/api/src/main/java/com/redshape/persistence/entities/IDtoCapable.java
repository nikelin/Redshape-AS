package com.redshape.persistence.entities;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.persistence.entities
 * @date 2/6/12 {2:52 PM}
 */
public interface IDtoCapable<T extends IDTO> {

    /**
     * Convert this entity object into comformable
     * Data Transfer Object which is unbind from Hibernate
     * or other platforms context.
     *
     * @return
     */
    public T toDTO();
    
    public T createDTO();

}
