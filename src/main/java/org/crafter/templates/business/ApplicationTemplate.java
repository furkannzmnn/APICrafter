package org.crafter.templates.business;

import org.crafter.Ioc;
import org.crafter.util.ClassValidation;
import org.crafter.util.ClassWriter;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.crafter.ProjectStarter.PROJECT_INFO;
import static org.crafter.util.ClassValidation.*;

public interface ApplicationTemplate extends Ioc {
    String prompt(String subject);

    void parseAnswer(String answer);

    Integer order();

    default List<String> defaultParseAnswer(String answer, String packageName) {
        List<String> allClasWithContent = extractClassStrings(answer);
        String directory = PROJECT_INFO.get("projectDirectory") + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator + "org" + File.separator + "crafter" + File.separator + packageName;

        Map<String, String> classes = new HashMap<>();

        for (String classWithContent : allClasWithContent) {
            String fixedContent = fixClassContentIfContentInvalid(classWithContent);
            if (ClassValidation.isValidClassName(fixedContent)) {
                String className = ClassWriter.getClassName(fixedContent);
                classes.put(className, fixedContent);
            }
        }
        classes.forEach((className, classContent) -> ClassWriter.writeClass(className, classContent, directory));

        return new ArrayList<>(classes.keySet());
    }


}
