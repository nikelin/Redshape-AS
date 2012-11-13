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
import org.hibernate.type.Type;

/**
 * @author Maulik Shah
 */
public class SetParameterEvent implements QueryEvent {
  private static enum CtorType {
    POSITION_VAL,
    POSITION_VAL_TYPE,
    NAME_VAL,
    NAME_VAL_TYPE,
  }

  private final CtorType ctorType;

  private final int position;
  private final Object val;
  private final Type type;
  private final String name;


  private SetParameterEvent(CtorType ctorType, int position, String name, Object val, Type type) {
    this.ctorType = ctorType;
    this.position = position;
    this.val = val;
    this.type = type;
    this.name = name;
  }

  public SetParameterEvent(int position, Object val, Type type) {
    this(CtorType.POSITION_VAL_TYPE, position, null, val, type);
  }

  public SetParameterEvent(String name, Object val, Type type) {
    this(CtorType.NAME_VAL_TYPE, -1, name, val, type);
  }

  public SetParameterEvent(int position, Object val) {
    this(CtorType.POSITION_VAL, position, null, val, null);
  }

  public SetParameterEvent(String name, Object val) {
    this(CtorType.NAME_VAL, -1, name, val, null);
  }

  public void onEvent(Query query) {
    switch(ctorType) {
      case POSITION_VAL:
        query.setParameter(position, val);
        break;
      case POSITION_VAL_TYPE:
        query.setParameter(position, val, type);
        break;
      case NAME_VAL:
        query.setParameter(name, val);
        break;
      case NAME_VAL_TYPE:
        query.setParameter(name, val, type);
        break;
      default:
        throw new ShardedSessionException(
            "Unknown ctor type in SetParameterEvent: " + ctorType);
    }
  }

}
