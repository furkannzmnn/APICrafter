package org.crafter.templates;

import org.crafter.util.ClassWriter;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static org.crafter.ProjectStarter.ANSWERS;
import static org.crafter.ProjectStarter.PROJECT_INFO;

public class RepositoryTemplate implements ApplicationTemplate {
    @Override
    public String prompt(String subject) {
        String modelTemplate = ANSWERS.get("ModelTemplate");
        return """
                %s konulu bir proje yazıyorum spring boot ile. Model sınıflarım bu şekilde
                %s
                Bana bu projede olması gereken repository sınıfları
                yazar mısın ? Yazarken sadece java sınıf kodları yaz.
                
                Kurallar şu şekildedir:
                1. Repository sınıfları interface olmalıdır.
                2. Cevapta sadece java kodları olmalıdır. Yorum satırı içermemelidir.
                3. Repository sınıfları org.crafter.repositories paketinde olmalıdır.
                4. İmportlar otomatik olarak eklenmelidir.
                5. En az model sayısı kadar repository sınıfı olmalıdır.
                6. Repository sınıfları JpaRepository interface'inden türemelidir.
                
                """.formatted(subject, modelTemplate);
    }

    @Override
    public void parseAnswer(String answer) {
        String[] lines = answer.split("\n");
        String className = "";
        StringBuilder classString = new StringBuilder();
        String directory = PROJECT_INFO.get("projectDirectory") + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator + "org" + File.separator + "crafter" + File.separator + "repositories";

        Map<String, String> classes = new HashMap<>();

        for (String line : lines) {
            if (line.contains("public interface")) {
                if (!className.isEmpty()) {
                    classes.put(className, classString.toString());
                    classString.setLength(0); // Clear the StringBuilder for the next class
                }
                className = line.split(" ")[2];
            }
            classString.append(line).append("\n"); // Append each line to the StringBuilder
        }

        // add last class
        if (!className.isEmpty()) {
            classes.put(className, classString.toString());
        }

        for (String name : classes.keySet()) {
            ClassWriter.writeClass(className, classes.get(name), directory);
        }
    }
}
