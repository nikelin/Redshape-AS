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

package com.redshape.forker;

import java.io.DataInputStream;
import java.io.DataOutputStream;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.forker
 * @inspiredBy Jukka Zitting <jukkaz.wordpress.com>
 * @date 1/31/12 {1:43 PM}
 */
public interface IFork {

    public DataInputStream getInput();

    public DataOutputStream getOutput();

    public int getPID();

    public boolean isPaused() throws ProcessException;

    public void resume() throws ProcessException;

    public void shutdown() throws ProcessException;

    public void pause() throws ProcessException;

}
