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
