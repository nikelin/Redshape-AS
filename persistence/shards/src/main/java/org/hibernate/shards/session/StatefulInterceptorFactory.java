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

import org.hibernate.Interceptor;

/**
 * Interface describing an object that knows how to create Interceptors.
 * Technically this is just an interceptor factory, but it is designed
 * to be used by clients who want to use stateful interceptors in conjunction
 * with sharded sessions.  Clients should make sure their Interceptor
 * implementation implements this interface.  Furthermore, if the
 * Interceptor implementation requires a reference to the Session, the
 * Interceptor returned by newInstance() should implement the {@link org.hibernate.shards.session.RequiresSession}
 * interface.
 *
 * @author maxr@google.com (Max Ross)
 */
public interface StatefulInterceptorFactory {
  Interceptor newInstance();
}
