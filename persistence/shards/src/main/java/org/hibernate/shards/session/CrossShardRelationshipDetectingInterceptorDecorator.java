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

package org.hibernate.shards.session;

import org.hibernate.CallbackException;
import org.hibernate.Interceptor;
import org.hibernate.shards.util.InterceptorDecorator;
import org.hibernate.type.Type;

import java.io.Serializable;

/**
 * Decorator that checks for cross shard relationships before delegating
 * to the decorated interceptor.
 *
 * @author maxr@google.com (Max Ross)
 */
class CrossShardRelationshipDetectingInterceptorDecorator extends InterceptorDecorator {

  private final CrossShardRelationshipDetectingInterceptor csrdi;

  public CrossShardRelationshipDetectingInterceptorDecorator(
      CrossShardRelationshipDetectingInterceptor csrdi,
      Interceptor delegate) {
    super(delegate);
    this.csrdi = csrdi;
  }

  @Override
  public boolean onFlushDirty(Object entity, Serializable id,
      Object[] currentState, Object[] previousState, String[] propertyNames,
      Type[] types) throws CallbackException {

    // first give the cross relationship detector a chance
    csrdi.onFlushDirty(entity, id, currentState, previousState, propertyNames, types);
    // now pass it on
    return
        delegate.onFlushDirty(
            entity,
            id,
            currentState,
            previousState,
            propertyNames,
            types);
  }

  @Override
  public void onCollectionUpdate(Object collection, Serializable key)
      throws CallbackException {
    // first give the cross relationship detector a chance
    csrdi.onCollectionUpdate(collection, key);
    // now pass it on
    delegate.onCollectionUpdate(collection, key);
  }

  CrossShardRelationshipDetectingInterceptor getCrossShardRelationshipDetectingInterceptor() {
    return csrdi;
  }
}
