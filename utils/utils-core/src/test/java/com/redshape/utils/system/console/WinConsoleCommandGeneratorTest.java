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
