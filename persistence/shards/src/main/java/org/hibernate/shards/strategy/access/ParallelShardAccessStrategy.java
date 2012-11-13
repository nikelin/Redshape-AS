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

package org.hibernate.shards.strategy.access;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.shards.Shard;
import org.hibernate.shards.ShardOperation;
import org.hibernate.shards.strategy.exit.ExitOperationsCollector;
import org.hibernate.shards.strategy.exit.ExitStrategy;
import org.hibernate.shards.util.Lists;
import org.hibernate.shards.util.Preconditions;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Invokes the given operation on the given org.hibernate.shards in parallel.
 * TODO(maxr) Add support for rejected tasks
 *
 * @author maxr@google.com (Max Ross)
 */
public class ParallelShardAccessStrategy implements ShardAccessStrategy {

  private final ThreadPoolExecutor executor;

  private final Log log = LogFactory.getLog(getClass());

  public ParallelShardAccessStrategy(ThreadPoolExecutor executor) {
    Preconditions.checkNotNull(executor);
    this.executor = executor;
  }

  public <T> T apply(
      List<Shard> shards,
      ShardOperation<T> operation,
      ExitStrategy<T> exitStrategy,
      ExitOperationsCollector exitOperationsCollector) {

    List<StartAwareFutureTask> tasks = Lists.newArrayListWithCapacity(shards.size());

    int taskId = 0;

    /**
     * Used to prevent threads for processing until all tasks have been
     * submitted, otherwise we risk tasks that want to cancel other tasks
     * that have not yet been scheduled.
     */
    CountDownLatch startSignal = new CountDownLatch(1);
    /**
     * Used to signal this thread that all processing is complete
     */
    CountDownLatch doneSignal = new CountDownLatch(shards.size());
    for(final Shard shard : shards) {
      // create a task for each shard
      ParallelShardOperationCallable<T> callable =
          new ParallelShardOperationCallable<T>(
              startSignal,
              doneSignal,
              exitStrategy,
              operation,
              shard,
              tasks);
      // wrap the task in a StartAwareFutureTask so that the task can be cancelled
      StartAwareFutureTask ft = new StartAwareFutureTask(callable, taskId++);
      tasks.add(ft);
      // hand the task off to the executor for execution
      executor.execute(ft);
    }
    // the tasks List is populated, release the threads!
    startSignal.countDown();
    try {
      log.debug("Waiting for threads to complete processing before proceeding.");
      //TODO(maxr) let users customize timeout behavior
      /*
      if(!doneSignal.await(10, TimeUnit.SECONDS)) {
        final String msg = "Parallel operations timed out.";
        log.error(msg);
        throw new HibernateException(msg);
      }
      */
      // now we wait until all threads finish
      doneSignal.await();
    } catch (InterruptedException e) {
      // not sure why this would happen or what we should do if it does
      log.error("Received unexpected exception while waiting for done signal.", e);
    }
    log.debug("Compiling results.");
    return exitStrategy.compileResults(exitOperationsCollector);
  }
}
