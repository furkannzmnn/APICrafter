package org.crafter.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.logging.Logger;

public class ClassWriter {
    private static final Logger LOGGER = Logger.getLogger(ClassWriter.class.getName());
    public static void writeClass(String className, String classContent, String directory) {
        File directoryFile = new File(directory);
        if (!directoryFile.exists()) {
            boolean mkdirs = directoryFile.mkdirs();
            if (!mkdirs) {
                LOGGER.warning("Klasör oluşturulamadı");
            }
        }

        File outputFile = new File(directory + File.separator + className + ".java");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            writer.write(classContent);
        } catch (Exception e) {
            LOGGER.warning(e.getMessage());
        }
    }

    public static String getClassName(String classWithContent) {
        String[] lines = classWithContent.split("\n");
        String className = "";
        for (String line : lines) {
            if (line.startsWith("public class") || line.startsWith("public interface")) {
                className = line.split(" ")[2];
            }
        }
        return className;
    }


}
