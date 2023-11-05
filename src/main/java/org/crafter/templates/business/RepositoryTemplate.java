package org.crafter.templates.business;

import org.crafter.Bean;

import java.util.List;

import static org.crafter.ProjectStarter.ANSWERS;

@Bean(name = "applicationTemplates")
public class RepositoryTemplate implements ApplicationTemplate {
    @Override
    public String prompt(String subject) {
        return STR."""
                Model sınıflarım bu şekilde
                \{ANSWERS.get("ModelTemplate")}
                Bana bu projede olması gereken repository sınıflarını yazar mısın ? Yazarken sadece java sınıf kodları yaz.

                Kurallar şu şekildedir:
                1. Repository sınıfları interface olmalıdır.
                2. Cevapta sadece java kodları olmalıdır. Yorum satırı içermemelidir.
                3. Repository sınıfları org.crafter.repositories paketinde olmalıdır.
                4. İmportlar otomatik olarak eklenmelidir.
                5. En az model sayısı kadar repository sınıfı olmalıdır.
                6. Repository sınıfları JpaRepository interface'inden türemelidir.
                7. Java kodu haricinde başka hiçbir şey yazma.

Yukarıdaki örnekte olduğu gibi, projede bulunması gereken repository sınıflarını ayrı ayrı tanımlayarak gönderir misiniz? Her repository sınıfı için ayrı bir blok şeklinde kod örneği bekliyorum.
                """;
    }

    @Override
    public void parseAnswer(String answer) {
        List<String> classes = defaultParseAnswer(answer, "repositories");
        ANSWERS.put(ServiceTemplate.class.getSimpleName(), String.join("\n\n", classes));
    }

    @Override
    public Integer order() {
        return 1;
    }
}
