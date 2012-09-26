package com.redshape.utils.system.console;

import junit.framework.Assert;
import org.junit.Test;

/**
 * User: Sergey Kidyaev
 * Date: 8/2/12
 * Time: 2:40 PM
 */
public class LinuxConsoleCommandGeneratorTest {
    private static final String DEFAULT_PATH = "root/sub";

    @Test
    public void testCreateDirCommand() {
        Assert.assertEquals("mkdir -p " + DEFAULT_PATH, new LinuxConsoleCommandGenerator().generateCreateDirCommand(DEFAULT_PATH));
    }

    @Test
    public void testDeleteDirCommand() {
        Assert.assertEquals("rm -r " + DEFAULT_PATH, new LinuxConsoleCommandGenerator().generateDeleteDirCommand(DEFAULT_PATH));
    }
}
