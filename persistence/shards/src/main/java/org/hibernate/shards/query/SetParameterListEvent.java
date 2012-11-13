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

import java.util.Collection;

/**
 * @author Maulik Shah
 */
public class SetParameterListEvent implements QueryEvent {

  private static enum CtorType {
    NAME_VALS_COLL_TYPE,
    NAME_VALS_COLL,
    NAME_VALS_OBJ_ARR,
    NAME_VALS_OBJ_ARR_TYPE
  }

  private final CtorType ctorType;
  private final String name;
  private final Collection valsColl;
  private final Object[] valsArr;
  private final Type type;

  private SetParameterListEvent(CtorType ctorType, String name, Collection valsColl, Object[] valsArr, Type type) {
    this.ctorType = ctorType;
    this.name = name;
    this.valsColl = valsColl;
    this.valsArr = valsArr;
    this.type = type;
  }

  public SetParameterListEvent(String name, Collection vals, Type type) {
    this(CtorType.NAME_VALS_COLL_TYPE, name, vals, null, type);
  }

  public SetParameterListEvent(String name, Collection vals) {
    this(CtorType.NAME_VALS_COLL, name, vals, null, null);
  }

  public SetParameterListEvent(String name, Object[] vals) {
    this(CtorType.NAME_VALS_OBJ_ARR, name, null, vals, null);
  }

  public SetParameterListEvent(String name, Object[] vals, Type type) {
    this(CtorType.NAME_VALS_OBJ_ARR_TYPE, name, null, vals, type);
  }


  public void onEvent(Query query) {
    switch(ctorType) {
      case NAME_VALS_COLL_TYPE:
        query.setParameterList(name, valsColl, type);
        break;
      case NAME_VALS_COLL:
        query.setParameterList(name, valsColl);
        break;
      case NAME_VALS_OBJ_ARR:
        query.setParameterList(name, valsArr);
        break;
      case NAME_VALS_OBJ_ARR_TYPE:
        query.setParameterList(name, valsArr, type);
        break;
      default:
        throw new ShardedSessionException(
            "Unknown ctor type in SetParameterListEvent: " + ctorType);
    }
  }

}
