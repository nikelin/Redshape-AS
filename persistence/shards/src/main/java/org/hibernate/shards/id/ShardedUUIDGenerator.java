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

import org.hibernate.dialect.Dialect;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.UUIDHexGenerator;
import org.hibernate.shards.ShardId;
import org.hibernate.shards.session.ShardedSessionImpl;
import org.hibernate.shards.util.Preconditions;
import org.hibernate.type.Type;
import org.hibernate.util.PropertiesHelper;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Properties;

/**
 * Supports generation of either 32-character hex String UUID or 128 bit
 * BigInteger UUID that encodes the shard.
 *
 * @author Tomislav Nad
 */
public class ShardedUUIDGenerator extends UUIDHexGenerator implements ShardEncodingIdentifierGenerator {

  private IdType idType;

  private static String ZERO_STRING = "00000000000000000000000000000000";
  private static String ID_TYPE_PROPERTY = "sharded-uuid-type";

  private static enum IdType { STRING, INTEGER }

  private int getShardId() {
    ShardId shardId = ShardedSessionImpl.getCurrentSubgraphShardId();
    Preconditions.checkState(shardId != null);
    return shardId.getId();
  }

  public ShardId extractShardId(Serializable identifier) {
    Preconditions.checkNotNull(identifier);
    String hexId;
    switch(idType) {
      case STRING:
        hexId = (String)identifier;
        return new ShardId(Integer.decode("0x" + hexId.substring(0, 4)));
      case INTEGER:
        String strippedHexId = ((BigInteger)identifier).toString(16);
        hexId = ZERO_STRING.substring(0, 32 - strippedHexId.length()) + strippedHexId;
        return new ShardId(Integer.decode("0x" + hexId.substring(0, hexId.length()-28)));
      default:
        // should never get here
        throw new IllegalStateException("ShardedUUIDGenerator was not configured properly");
    }
  }

  @Override
  public Serializable generate(SessionImplementor session, Object object) {
    String id =  new StringBuilder(32).append(format((short)getShardId()))
                                      .append(format(getIP()))
                                      .append(format((short)(getJVM()>>>16)))
                                      .append(format(getHiTime()))
                                      .append(format(getLoTime()))
                                      .append(format(getCount()))
                                      .toString();
    switch(idType) {
      case STRING:
        return id;
      case INTEGER:
        return new BigInteger(id, 16);
      default:
        // should never get here
        throw new IllegalStateException("ShardedUUIDGenerator was not configured properly");
    }
  }

  @Override
  public void configure(Type type, Properties params, Dialect d) {
    this.idType = IdType.valueOf(PropertiesHelper.getString(ID_TYPE_PROPERTY, params, "INTEGER"));
  }

}
