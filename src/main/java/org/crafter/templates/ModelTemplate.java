package org.crafter.templates;

public class ModelTemplate implements ApplicationTemplate{
    @Override
    public String prompt(String subject) {
        return """
                %s konulu bir proje yazıyorum spring boot ile. Bana bu projede olması gereken model sınıfları
                fieldları ile birlikte yazar mısın ? Yazarken sadece java sınıf kodları yaz.
                """.formatted(subject);
    }

    @Override
    public void parseAnswer(String answer) {

    }
}
