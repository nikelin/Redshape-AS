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

import java.util.Calendar;

/**
 * @author Maulik Shah
 */
public class SetCalendarEvent implements QueryEvent {

   private static enum CtorType {
    POSITION_VAL,
    NAME_VAL
  }

  private final CtorType ctorType;
  private final int position;
  private final Calendar val;
  private final String name;

  private SetCalendarEvent(CtorType ctorType, int position, Calendar val, String name) {
    this.ctorType = ctorType;
    this.position = position;
    this.val = val;
    this.name = name;
  }

  public SetCalendarEvent(int position, Calendar val) {
    this(CtorType.POSITION_VAL, position, val, null);
  }

  public SetCalendarEvent(String name, Calendar val) {
    this(CtorType.NAME_VAL, -1, val, name);
  }

  public void onEvent(Query query) {
    switch(ctorType) {
      case POSITION_VAL:
        query.setCalendar(position, val);
        break;
      case NAME_VAL:
        query.setCalendar(name, val);
        break;
      default:
        throw new ShardedSessionException(
            "Unknown ctor type in SetCalendarEvent: " + ctorType);
    }
  }

}
