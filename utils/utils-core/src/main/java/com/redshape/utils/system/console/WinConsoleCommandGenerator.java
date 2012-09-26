package com.redshape.utils.system.console;

/**
 * Generates console commands for Win OS.
 * <p/>
 * User: Sergey Kidyaev
 * Date: 8/2/12
 * Time: 1:16 PM
 */
public class WinConsoleCommandGenerator implements ConsoleCommandGenerator {

    public static final String COMMAND_INTERPRETER = "cmd /c ";

    @Override
    public String generateCreateDirCommand(String path) {
        return COMMAND_INTERPRETER + "mkdir " + path;    }

    @Override
    public String generateDeleteDirCommand(String path) {
        return COMMAND_INTERPRETER + "rmdir /S /Q " + path;
    }
}
