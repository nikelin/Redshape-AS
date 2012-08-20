package com.redshape.utils.system.console;

/**
 * Generates console commands for Linux OS.
 * <p/>
 * User: Sergey Kidyaev
 * Date: 8/2/12
 * Time: 1:27 PM
 */
public class LinuxConsoleCommandGenerator implements ConsoleCommandGenerator{
    @Override
    public String generateCreateDirCommand(String path) {
        return "mkdir -p " + path;
    }

    @Override
    public String generateDeleteDirCommand(String path) {
        return "rm -r " + path;
    }
}
