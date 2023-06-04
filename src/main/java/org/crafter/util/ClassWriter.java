package org.crafter.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.logging.Logger;

public class ClassWriter {
    private static final Logger LOGGER = Logger.getLogger(ClassWriter.class.getName());
    public static void writeClass(String className, String classContent, String directory) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("project/" +className + ".class"))) {
            writer.write(classContent);
        } catch (Exception e) {
            LOGGER.warning(e.getMessage());
        }
    }
}
