package com.redshape.persistence.entities;

import com.redshape.persistence.DaoContextHolder;
import com.redshape.persistence.dao.DAOException;
import com.redshape.persistence.dao.DAOFacade;
import com.redshape.persistence.dao.IDAO;
import com.redshape.persistence.utils.ISessionManager;
import com.redshape.utils.Commons;
import com.redshape.utils.beans.Property;
import com.redshape.utils.beans.PropertyUtils;
import org.apache.log4j.Logger;

import java.beans.IntrospectionException;
import java.util.*;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.persistence.entities
 * @date 2/6/12 {4:04 PM}
 */
public final class DtoUtils {
    private static final Logger log = Logger.getLogger(DtoUtils.class);

    private static final Map<IDtoCapable, IDTO> cache = new HashMap<IDtoCapable, IDTO>();
    private static final Map<IEntity, IEntity> reverseCache = new HashMap<IEntity, IEntity>();
    
    public static <T extends IEntity> T fromDTO( IEntity entity ) {
        try {
            Commons.checkNotNull(DaoContextHolder.instance().getContext(), "Global context not wired");

            if ( entity == null ) {
                return null;
            }

            if ( !entity.isDto() ) {
                return (T) entity;
            }
            
            final IDTO dto = (IDTO) entity;
            
            IEntity result = reverseCache.get(dto);
            if ( result != null ) {
                return (T) result;
            }
//
            Class<? extends IEntity> entityClazz = dto.getEntityClass();
            if ( entityClazz == null ) {
                throw new IllegalStateException("<null>");
            }
        
            if ( dto.getId() != null ) {
                DAOFacade facade = DaoContextHolder.instance().getContext().getBean(DAOFacade.class);
        
                IDAO<? extends IEntity> dao = facade.getDAO(entityClazz);
                Commons.checkNotNull(dao, "DAO not registered");
                result = dao.findById( dto.getId() );
            } else {
                try {
                    result = entityClazz.newInstance();
                } catch ( Throwable e ) {
                    throw new DAOException( e.getMessage(), e );
                }
            }
            
            reverseCache.put(dto, result = fromDTO(result, dto));
        
            return (T) result;
        } catch ( DAOException e ) {
            log.error( e.getMessage(), e );
            throw new IllegalStateException( e.getMessage(), e );
        }
    }          
    
    protected static IEntity fromDTO( IEntity entity, IEntity dto ) throws DAOException {
        try {
            for ( Property property : PropertyUtils.getInstance().getProperties(entity.getClass()) ) {
                for ( Property dtoProperty : PropertyUtils.getInstance().getProperties(dto.getClass()) ) {
                    try {
                        if ( !dtoProperty.getName().equals( property.getName() ) ) {
                            continue;
                        }
    
                        Object value = dtoProperty.get(dto);
                        if ( value == entity ) {
                            value = null;
                        }

                        if ( value != null ) {
                            if ( value instanceof IDTO ) {
                                value = DtoUtils.fromDTO( (IEntity) value );
                            } else if ( isListType(value) ) {
                                value = processList(value);
                            }
                        }
    
                        property.set( entity, value );
                        break;
                    } catch ( Throwable e ) {
                        log.error( e.getMessage(), e );
                    }
                }
            }
    
            return entity;
        } catch ( IntrospectionException e ) {
            throw new DAOException( e.getMessage(), e );
        }
    }
    
    protected static Collection<?> processList( Object value ) throws DAOException {
        Collection result;

        Class<?> valueClazz = value.getClass();
        if ( List.class.isAssignableFrom(valueClazz) ) {
            result = new ArrayList();
        } else if ( Set.class.isAssignableFrom(valueClazz) ) {
            result = new HashSet();
        } else if ( Queue.class.isAssignableFrom(valueClazz) ) {
            result = new LinkedBlockingQueue();
        } else if ( Deque.class.isAssignableFrom(valueClazz) ) {
            result = new LinkedBlockingDeque();
        } else {
            result = new HashSet();
        }

        boolean entitiesCollection = true;

        openSession();
        Object[] collection = new Object[ ( (Collection) value ).size() ];
        for ( Object part : collection ) {
            if ( part == null ) {
                continue;
            }

            if ( entitiesCollection && part instanceof IEntity
                    && ((IEntity) part).isDto() ) {
                IEntity item = fromDTO( (IEntity) part );
                if ( item == null ) {
                    continue;
                }

                result.add( item );
                entitiesCollection = true;
            } else {
                result.add( part );
                entitiesCollection = false;
            }
        }

        return result;
    } 
    
    protected static boolean isListType( Object value ) {
        return Collection.class.isAssignableFrom(value.getClass());
    }
    
    public static <T extends IDTO, V extends IDtoCapable<T> & IEntity> T toDTO( V entity ) {
        try {
//            if ( cache.get(entity) != null ) {
//                return (T) cache.get(entity);
//            }

            openSession();

            entity = getSessionManager().refresh(entity);

            T dto = entity.createDTO();
//            cache.put(entity, dto);
            Commons.checkNotNull(dto);

            for ( Property property : PropertyUtils.getInstance().getProperties(entity.getClass()) ) {
                for ( Property dtoProperty : PropertyUtils.getInstance().getProperties(dto.getClass()) ) {
                    try {
                        if ( !dtoProperty.getName().equals( property.getName() ) ) {
                            continue;
                        }

                        Object value = property.get(entity);
                        if ( value == entity ) {
                            value = null;
                        }

                        if ( value != null ) {
                            if ( value != null && value instanceof IDtoCapable ) {
                                value = ( (IDtoCapable) value ).toDTO();
                            } else if ( isListType(value) ) {
                                value = processList(value);
                            }
                        }

                        dtoProperty.set( dto, value );
                    } catch ( Throwable e ) {
                        log.error( e.getMessage(), e );
                    }
                }
            }

            return dto;
        } catch ( Throwable e ) {
            throw new IllegalStateException( e.getMessage(), e );
        }
    }

    protected static ISessionManager getSessionManager() {
        return DaoContextHolder.instance().getContext().getBean(ISessionManager.class);
    }

    protected static void openSession() throws DAOException {
        getSessionManager().open();
    }

    protected static void closeSession() throws DAOException {
        getSessionManager().close();
    }

}
