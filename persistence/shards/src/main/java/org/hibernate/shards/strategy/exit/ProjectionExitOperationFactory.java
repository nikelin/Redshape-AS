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

package org.hibernate.shards.strategy.exit;

import org.hibernate.criterion.AggregateProjection;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.RowCountProjection;
import org.hibernate.engine.SessionFactoryImplementor;

/**
 * @author Maulik Shah
 */
public class ProjectionExitOperationFactory {

  private final static ProjectionExitOperationFactory projectionExitOperationFactory
      = new ProjectionExitOperationFactory();

  private ProjectionExitOperationFactory() {}

  public static ProjectionExitOperationFactory getFactory() {
    return projectionExitOperationFactory;
  }

  public ProjectionExitOperation getProjectionExitOperation(Projection projection, SessionFactoryImplementor sessionFactoryImplementor) {
    if (projection instanceof RowCountProjection) {
      return new RowCountExitOperation(projection);
    }
    if (projection instanceof AggregateProjection) {
      return new AggregateExitOperation((AggregateProjection) projection);
    }


    // TODO(maulik) support these projections, hopefully not by creating ShardedProjections
    // Projection rowCount() {
    // AggregateProjection avg(String propertyName) {
    // CountProjection count(String propertyName) {
    // Projection distinct(Projection proj) {
    // ProjectionList projectionList() {
    // CountProjection countDistinct(String propertyName) {
    // Projection sqlProjection(String sql, String[] columnAliases, Type[] types) {
    // Projection sqlGroupProjection(String sql, String groupBy, String[] columnAliases, Type[] types) {
    // PropertyProjection groupProperty(String propertyName) {
    // PropertyProjection property(String propertyName) {
    // IdentifierProjection id() {
    // Projection alias(Projection projection, String alias) {

    throw new UnsupportedOperationException(
        "This projection is unsupported: " + projection.getClass());
  }

}
