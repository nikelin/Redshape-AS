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

package com.redshape.utils.system.console;

/**
 * Generates console commands depending on environment (Operating System).
 * <p/>
 * User: Sergey Kidyaev
 * Date: 8/2/12
 * Time: 1:12 PM
 */
public interface ConsoleCommandGenerator {
    /**
     * Generates command to create a directory.
     *
     * @return command
     */
    public String generateCreateDirCommand(String path);

    /**
     * Generates command to delete a directory.
     *
     * @return command
     */
    public String generateDeleteDirCommand(String path);
}
