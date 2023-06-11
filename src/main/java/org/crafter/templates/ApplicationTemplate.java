package org.crafter.templates;

import org.crafter.Ioc;
import org.crafter.util.ClassWriter;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.crafter.ProjectStarter.PROJECT_INFO;

public interface ApplicationTemplate extends Ioc {
    String prompt(String subject);

    void parseAnswer(String answer);

    default void defaultParseAnswer(String answer, String packageName) {
        String[] lines = answer.split("\n");
        String className = "";
        List<String> classNames = new ArrayList<>();
        StringBuilder classString = new StringBuilder();
        String directory = PROJECT_INFO.get("projectDirectory") + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator + "org" + File.separator + "crafter" + File.separator + packageName;
        Map<String, String> classes = new HashMap<>();

        for (String line : lines) {
            if (line.contains("public class")) {
                if (!className.isEmpty()) {
                    classes.put(className, classString.toString());
                    classString.setLength(0); // Clear the StringBuilder for the next class
                }
                className = line.split(" ")[2];
                classNames.add(className);
            }
            classString.append(line).append("\n"); // Append each line to the StringBuilder
        }

        // add last class
        if (!className.isEmpty()) {
            classes.put(className, classString.toString());
        }

        for (String name : classNames) {
            ClassWriter.writeClass(name, classes.get(name), directory);
        }
    }
}
