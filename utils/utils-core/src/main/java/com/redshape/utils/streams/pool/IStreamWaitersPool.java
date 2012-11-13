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

package com.redshape.utils.streams.pool;

import com.redshape.utils.TimeSpan;
import com.redshape.utils.streams.IStreamWaiter;

import java.util.List;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.utils.streams
 * @date 1/31/12 {5:11 PM}
 */
public interface IStreamWaitersPool {

    public void addWaiter( IStreamWaiter waiter );

    public void removeWaiter( IStreamWaiter waiter );

    public List<IStreamWaiter> getWaiters();

    public void clearWaiters();

    public void await();

    public void awaitUntil( TimeSpan span ) throws InterruptedException;

}
