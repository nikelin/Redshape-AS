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
import org.hibernate.shards.session.ShardedSessionException;

/**
 * Event that allows an alias to be lazily added to a Criteria.
 * @see Criteria#createAlias(String, String)
 * @see Criteria#createAlias(String, String, int)
 * @see Criteria#createAlias(String, String, int) 
 *
 * @author maxr@google.com (Max Ross)
 */
class CreateAliasEvent implements CriteriaEvent {

  private enum MethodSig {
    ASSOC_PATH_AND_ALIAS,
    ASSOC_PATH_AND_ALIAS_AND_JOIN_TYPE
  }

  // the signature of the createAlias method we're going to invoke when
  // the event fires
  private final MethodSig methodSig;

  // the association path
  private final String associationPath;

  // the name of the alias we're creating
  private final String alias;

  // the join type - we look at method sig to see if we should use it
  private final Integer joinType;

  /**
   * Construct a CreateAliasEvent
   *
   * @param methodSig the signature of the createAlias method we're going to invoke
   * when the event fires
   * @param associationPath the association path of the alias we're creating.
   * @param alias the name of the alias we're creating.
   * @param joinType the join type of the alias we're creating.  Can be null.
   */
  private CreateAliasEvent(
      MethodSig methodSig,
      String associationPath,
      String alias,
      /*@Nullable*/Integer joinType) {
    this.methodSig = methodSig;
    this.associationPath = associationPath;
    this.alias = alias;
    this.joinType = joinType;
  }

  /**
   * Construct a CreateAliasEvent
   *
   * @param associationPath the association path of the alias we're creating.
   * @param alias the name of the alias we're creating.
   */
  public CreateAliasEvent(String associationPath, String alias) {
    this(MethodSig.ASSOC_PATH_AND_ALIAS, associationPath, alias, null);
  }

  /**
   * Construct a CreateAliasEvent
   *
   * @param associationPath the association path of the alias we're creating.
   * @param alias the name of the alias we're creating.
   * @param joinType the join type of the alias we're creating.
   */
  public CreateAliasEvent(String associationPath, String alias, int joinType) {
    this(MethodSig.ASSOC_PATH_AND_ALIAS_AND_JOIN_TYPE, associationPath, alias,
        joinType);
  }

  public void onEvent(Criteria crit) {
    switch (methodSig) {
      case ASSOC_PATH_AND_ALIAS:
        crit.createAlias(associationPath, alias);
        break;
      case ASSOC_PATH_AND_ALIAS_AND_JOIN_TYPE:
        crit.createAlias(associationPath, alias, joinType);
        break;
      default:
        throw new ShardedSessionException(
            "Unknown ctor type in CreateAliasEvent: " + methodSig);
    }
  }
}
