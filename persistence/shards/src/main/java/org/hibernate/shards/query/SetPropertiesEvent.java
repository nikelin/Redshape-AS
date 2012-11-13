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

package org.hibernate.shards.query;

import org.hibernate.Query;
import org.hibernate.shards.session.ShardedSessionException;

import java.util.Map;

/**
 * @author Maulik Shah
 */
public class SetPropertiesEvent implements QueryEvent {

  private enum MethodSig {
    OBJECT, MAP
  }

  private final MethodSig sig;
  private final Object bean;
  private final Map map;

  public SetPropertiesEvent(Object bean) {
    this(MethodSig.OBJECT, bean, null);
  }

  public SetPropertiesEvent(Map map) {
    this(MethodSig.MAP, null, map);
  }

  private SetPropertiesEvent(MethodSig sig, Object bean, Map map) {
    this.sig = sig;
    this.bean = bean;
    this.map = map;
  }

  public void onEvent(Query query) {
    switch (sig) {
      case OBJECT:
        query.setProperties(bean);
        break;
      case MAP:
        query.setProperties(map);
        break;
      default:
        throw new ShardedSessionException(
            "Unknown sig in SetPropertiesEvent: " + sig);
    }
  }

}
