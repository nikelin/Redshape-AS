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
