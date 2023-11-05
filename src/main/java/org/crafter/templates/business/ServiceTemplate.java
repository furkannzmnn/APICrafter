package org.crafter.templates.business;

import org.crafter.Bean;

import static org.crafter.ProjectStarter.ANSWERS;
@Bean(name = "applicationTemplates")
public class ServiceTemplate implements ApplicationTemplate {

    @Override
    public String prompt(String subject) {
        String repositoryAnswer = ANSWERS.get(RepositoryTemplate.class.getSimpleName());
        return STR."""
                Bana bu projede olması gereken servis sınıflarını
                yazar mısın ? Yazarken sadece java sınıf kodları yaz. Projenin Repository sınıfları aşşağıdaki gibidir
                \{repositoryAnswer}
                Servis kuralları şu şekildedir:
                1. Servis sınıfları interface olmamalıdır.
                2. Methodların içi dolu olmalıdır ve db ile iletişime geçmelidir ve yorum satırı içermemeli.
                3. Servis sınıfları org.crafter.services paketinde olmalıdır.
                4. İmportlar otomatik olarak eklenmelidir.
                5. En az model sayısı kadar servis sınıfı olmalıdır.      
                Cevap olarak yalnızca kod ver, yorum satırı yazma.
                """;
    }

    @Override
    public void parseAnswer(String answer) {
        defaultParseAnswer(answer, "services");
    }

    @Override
    public Integer order() {
        return 2;
    }
}
