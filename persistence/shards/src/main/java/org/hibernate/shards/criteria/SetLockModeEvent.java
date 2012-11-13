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

package org.hibernate.shards.criteria;

import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.shards.session.ShardedSessionException;

/**
 * Event that allows the {@link LockMode} of a {@link Criteria} to be set lazily.
 * @see Criteria#setLockMode(LockMode)
 *
 * @author maxr@google.com (Max Ross)
 */
class SetLockModeEvent implements CriteriaEvent {

  private enum MethodSig {
    LOCK_MODE,
    LOCK_MODE_AND_ALIAS
  }

  // tells us which overload of setLockMode to use
  private final MethodSig methodSig;

  // the LockMode we'll set on the Criteria when the event fires
  private final LockMode lockMode;

  // the alias for which we'll set the lock mode on the Criteria when the event
  // fires.  Can be null
  private final String alias;

  /**
   * Construct a SetLockModeEvent
   *
   * @param methodSig tells us which overload of setLockMode to use
   * @param lockMode the lock mode we'll set when the event fires
   * @param alias the alias for which we'll set the lcok mode when the event
   * fires.  Can be null.
   */
  private SetLockModeEvent(
      MethodSig methodSig,
      LockMode lockMode,
      /*@Nullable*/ String alias) {
    this.methodSig = methodSig;
    this.lockMode = lockMode;
    this.alias = alias;
  }

  /**
   * Construct a SetLockModeEvent
   *
   * @param lockMode the lock mode we'll set when the event fires
   */
  public SetLockModeEvent(LockMode lockMode) {
    this(MethodSig.LOCK_MODE, lockMode, null);
  }

  /**
   * Construct a SetLockModeEvent
   *
   * @param lockMode the lock mode we'll set when the event fires
   * @param alias the alias for which we'll set the lock mode
   * when the event fires
   */
  public SetLockModeEvent(LockMode lockMode, String alias) {
    this(MethodSig.LOCK_MODE_AND_ALIAS, lockMode, alias);
  }

  public void onEvent(Criteria crit) {
    switch (methodSig) {
      case LOCK_MODE:
        crit.setLockMode(lockMode);
        break;
      case LOCK_MODE_AND_ALIAS:
        crit.setLockMode(alias, lockMode);
        break;
      default:
        throw new ShardedSessionException(
            "Unknown constructor type for SetLockModeEvent: " + methodSig);
    }
  }
}
