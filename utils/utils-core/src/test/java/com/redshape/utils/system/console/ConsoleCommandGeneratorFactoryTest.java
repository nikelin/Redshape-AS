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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * User: Sergey Kidyaev
 * Date: 8/2/12
 * Time: 1:54 PM
 */
public class ConsoleCommandGeneratorFactoryTest {
    public static final String SYSTEM_PROPERTY_OS_NAME = "os.name";

    private String operationSystemName;

    @Before
    public void setUp() {
        operationSystemName = System.getProperty(SYSTEM_PROPERTY_OS_NAME);
    }

    @After
    public void tearDown() {
        System.setProperty(SYSTEM_PROPERTY_OS_NAME, operationSystemName);
    }

    @Test
    public void testLinuxConsoleCommandGeneratorCreation() {
        System.setProperty(SYSTEM_PROPERTY_OS_NAME, "Linux Ubuntu");
        ConsoleCommandGenerator generator = ConsoleCommandGeneratorFactory.createConsoleCommandGenerator();
        assertNotNull(generator);
        assertTrue(generator instanceof LinuxConsoleCommandGenerator);
    }

    @Test
    public void testWinConsoleCommandGeneratorCreation() {
        System.setProperty(SYSTEM_PROPERTY_OS_NAME, "Windows 7");
        ConsoleCommandGenerator generator = ConsoleCommandGeneratorFactory.createConsoleCommandGenerator();
        assertNotNull(generator);
        assertTrue(generator instanceof WinConsoleCommandGenerator);
    }

    @Test (expected = UnsupportedOperationException.class)
    public void testUnknownConsoleCommandGeneratorCreation() {
        System.setProperty(SYSTEM_PROPERTY_OS_NAME, "Mac OS");
        ConsoleCommandGeneratorFactory.createConsoleCommandGenerator();
    }

}
