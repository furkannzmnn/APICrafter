package org.crafter.templates;

import java.io.File;
import java.io.FileWriter;

public class ServiceTemplate implements ApplicationTemplate{
    @Override
    public String prompt(String subject) {
        return """
                %s konulu bir proje yazıyorum spring boot ile. Bana bu projede olması gereken servis sınıfları
                fieldları ile birlikte yazar mısın ? Yazarken sadece java sınıf kodları yaz.
                """.formatted(subject);
    }

    @Override
    public void parseAnswer(String answer) {
        // write answer to file with .java extension -> /sınıflar  dosyası altına

        File file = new File("model.java");
        if (file.exists()) {
            System.out.println("Bu dosya zaten var.");
        } else {
            try {
                file.createNewFile();
            } catch (Exception e) {
                System.out.println("Dosya oluşturulamadı.");
            }
        }

        FileWriter writer = null;
        try {
            writer = new FileWriter(file);
            writer.write(answer);
            writer.close();
        } catch (Exception e) {
            System.out.println("Dosya yazılamadı.");
        }
    }
}
