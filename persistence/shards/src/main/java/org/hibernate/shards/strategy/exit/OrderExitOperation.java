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

import org.hibernate.criterion.Order;
import org.hibernate.shards.util.Preconditions;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Maulik Shah
 */
public class OrderExitOperation implements ExitOperation {

  private final Order order;
  private final String propertyName;

  public OrderExitOperation(Order order) {
    //TODO(maulik) support Ignore case!
    Preconditions.checkState(order.toString().endsWith("asc") ||
                             order.toString().endsWith("desc"));

    this.order = order;
    this.propertyName = getSortingProperty(order);
  }

  public List<Object> apply(List<Object> results) {
    List<Object> nonNullList = ExitOperationUtils.getNonNullList(results);
    Comparator<Object> comparator = new Comparator<Object>() {
      public int compare(Object o1, Object o2) {
        if (o1 == o2) {
          return 0;
        }
        Comparable<Object> o1Value = ExitOperationUtils.getPropertyValue(o1, propertyName);
        Comparable<Object> o2Value = ExitOperationUtils.getPropertyValue(o2, propertyName);
        if (o1Value == null) {
          return -1;
        }
        return o1Value.compareTo(o2Value);
      }
    };

    Collections.sort(nonNullList, comparator);
    if (order.toString().endsWith("desc")) {
      Collections.reverse(nonNullList);
    }

    return nonNullList;
  }

  private static String getSortingProperty(Order order) {
    /**
     * This method relies on the format that Order is using:
     * propertyName + ' ' + (ascending?"asc":"desc")
     */
    String str = order.toString();
    return str.substring(0, str.indexOf(' '));
  }

  
}
