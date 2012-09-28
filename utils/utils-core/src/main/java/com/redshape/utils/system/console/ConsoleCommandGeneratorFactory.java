package com.redshape.utils.system.console;

/**
 * Creates {@link ConsoleCommandGenerator} imlementation depending on environment (Operating System).
 *
 * User: Sergey Kidyaev
 * Date: 8/2/12
 * Time: 1:29 PM
 */
public class ConsoleCommandGeneratorFactory {
    private static ConsoleCommandGenerator winConsoleCommandGenerator   = new WinConsoleCommandGenerator();
    private static ConsoleCommandGenerator linuxConsoleCommandGenerator = new LinuxConsoleCommandGenerator();

    public static ConsoleCommandGenerator createConsoleCommandGenerator() {
        String operatingSystemName = getOperatingSystemName();

        if ( operatingSystemName.startsWith("Windows") ) {
            return winConsoleCommandGenerator;
        } else if ( operatingSystemName.startsWith("Linux") ) {
            return linuxConsoleCommandGenerator;
        } else {
            throw new UnsupportedOperationException("Unknown OS: " + operatingSystemName);
        }

    }

    protected static String getOperatingSystemName() {
        return System.getProperty("os.name");
    }
}
