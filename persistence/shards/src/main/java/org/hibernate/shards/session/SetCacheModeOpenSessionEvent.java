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

import org.hibernate.CacheMode;
import org.hibernate.Session;

/**
 * OpenSessionEvent which sets the CacheMode.
 *
 * @author maxr@google.com (Max Ross)
 */
class SetCacheModeOpenSessionEvent implements OpenSessionEvent {

  private final CacheMode cacheMode;

  public SetCacheModeOpenSessionEvent(CacheMode cacheMode) {
    this.cacheMode = cacheMode;
  }

  public void onOpenSession(Session session) {
    session.setCacheMode(cacheMode);
  }
}
