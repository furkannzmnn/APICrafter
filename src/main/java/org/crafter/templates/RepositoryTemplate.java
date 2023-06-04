package org.crafter.templates;

import static org.crafter.adapters.OpenAIAdapter.ANSWERS;

public class RepositoryTemplate implements ApplicationTemplate {
    @Override
    public String prompt(String subject) {
        String modelTemplate = ANSWERS.get("ModelTemplate");
        return """
                %s konulu bir proje yazıyorum spring boot ile. Model sınıflarım bu şekilde
                %s
                Bana bu projede olması gereken repository sınıfları
                yazar mısın ? Yazarken sadece java sınıf kodları yaz.
                """.formatted(subject, modelTemplate);
    }

    @Override
    public void parseAnswer(String answer) {
        String[] classStrings = answer.split("(?<=\\})\n\n+(?=[A-Za-z]|\\s*package)");
        for (String classString : classStrings) {
            System.out.println(classString);
        }
    }


}
