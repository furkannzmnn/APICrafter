package org.crafter.templates;

import org.crafter.util.ClassWriter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.crafter.ProjectStarter.PROJECT_INFO;
import static org.crafter.adapters.OpenAIAdapter.ANSWERS;

public class ServiceTemplate implements ApplicationTemplate {

    @Override
    public String prompt(String subject) {
        String repositoryAnswer = ANSWERS.get(RepositoryTemplate.class.getSimpleName());
        return """
                %s konulu bir proje yazıyorum spring boot ile. Bana bu projede olması gereken servis sınıflarını
                yazar mısın ? Yazarken sadece java sınıf kodları yaz. Projenin Repository sınıfları aşşağıdaki gibidir
                %s
                Servis kuralları şu şekildedir:
                1. Servis sınıfları interface olmamalıdır.
                2. Methodların içi dolu olmalıdır.                               
                Cevap olarak yalnızca kod ver, yorum satırı yazma.
                """.formatted(subject, repositoryAnswer);
    }

    @Override
    public void parseAnswer(String answer) {
        String[] lines = answer.split("\n");
        String className = "";
        List<String> classNames = new ArrayList<>();
        StringBuilder classString = new StringBuilder();
        String directory = PROJECT_INFO.get("projectDirectory") + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator + "org" + File.separator + "crafter" + File.separator + "services";

        // Gelen sınıfları parse et ve her sınıfı bir mape ekle, key olarak sınıfın ismini kullan
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

        // Son sınıfı ekle
        if (!className.isEmpty()) {
            classes.put(className, classString.toString());
        }

        // Parse edilen sınıfları yazdır
        for (String name : classNames) {
            writeEfficientFile(name, classes.get(name), directory);
        }
    }


    private void writeEfficientFile(String className, String classString, String directory) {
        ClassWriter.writeClass(className, classString, directory);
    }
}
