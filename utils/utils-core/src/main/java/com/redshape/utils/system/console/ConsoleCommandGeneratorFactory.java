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
