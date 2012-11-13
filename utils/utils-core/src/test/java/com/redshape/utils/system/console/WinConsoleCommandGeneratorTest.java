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

import junit.framework.Assert;
import org.junit.Test;

/**
 * User: Sergey Kidyaev
 * Date: 8/2/12
 * Time: 2:25 PM
 */
public class WinConsoleCommandGeneratorTest {
    private static final String DEFAULT_PATH = "root\\sub";

    @Test
    public void testCreateDirCommand() {
        Assert.assertEquals(WinConsoleCommandGenerator.COMMAND_INTERPRETER + "mkdir " + DEFAULT_PATH, new WinConsoleCommandGenerator().generateCreateDirCommand(DEFAULT_PATH));
    }

    @Test
    public void testDeleteDirCommand() {
        Assert.assertEquals(WinConsoleCommandGenerator.COMMAND_INTERPRETER + "rmdir /S /Q " + DEFAULT_PATH, new WinConsoleCommandGenerator().generateDeleteDirCommand(DEFAULT_PATH));
    }
}
