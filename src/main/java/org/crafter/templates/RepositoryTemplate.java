package org.crafter.templates;

public class RepositoryTemplate implements ApplicationTemplate{
    @Override
    public String prompt(String subject) {
        return """
                %s konulu bir proje yazıyorum spring boot ile. Bana bu projede olması gereken repository sınıfları
                yazar mısın ? Yazarken sadece java sınıf kodları yaz.
                """.formatted(subject);
    }

    @Override
    public void parseAnswer(String answer) {

    }
}
