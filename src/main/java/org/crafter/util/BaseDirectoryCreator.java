package org.crafter.util;

import java.io.File;
import java.util.logging.Logger;

public class BaseDirectoryCreator {
    private static final Logger LOGGER = Logger.getLogger(BaseDirectoryCreator.class.getName());

    private BaseDirectoryCreator() {
        throw new IllegalStateException("Utility class");
    }

    public static String createBaseDirectory(String directory) {
        File directoryFile = new File(directory);
        if (!directoryFile.exists()) {
            boolean mkdirs = directoryFile.mkdirs();
            if (!mkdirs) {
                LOGGER.warning("Klasör oluşturulamadı");
            }
        }
        return directory;
    }

}
