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

package org.hibernate.shards.id;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.TableHiLoGenerator;
import org.hibernate.shards.session.ControlSessionProvider;

import java.io.Serializable;

/**
 * TableHiLoGenerator which uses control shard to store table with hi values.
 *
 * @see org.hibernate.id.TableHiLoGenerator
 * @author Tomislav Nad
 */

public class ShardedTableHiLoGenerator extends TableHiLoGenerator implements GeneratorRequiringControlSessionProvider {

  private ControlSessionProvider controlSessionProvider;

  @Override
  public Serializable generate(SessionImplementor session, Object obj)
      throws HibernateException {
    Serializable id;
    SessionImplementor controlSession = null;
    try {
      controlSession = controlSessionProvider.openControlSession();
      id = superGenerate(controlSession, obj);
    } finally {
      if (controlSession != null) {
        ((Session)controlSession).close();
      }
    }
    return id;
  }

  public void setControlSessionProvider(ControlSessionProvider provider) {
    this.controlSessionProvider = provider;
  }

  Serializable superGenerate(SessionImplementor controlSession, Object obj) {
    return super.generate(controlSession, obj);
  }
}
