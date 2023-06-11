package org.crafter.templates;

import org.crafter.Bean;

import static org.crafter.ProjectStarter.ANSWERS;
@Bean(name = "applicationTemplates")
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
                2. Methodların içi dolu olmalıdır ve db ile iletişime geçmelidir ve yorum satırı içermemeli.
                3. Servis sınıfları org.crafter.services paketinde olmalıdır.
                4. İmportlar otomatik olarak eklenmelidir.
                5. En az model sayısı kadar servis sınıfı olmalıdır.      
                Cevap olarak yalnızca kod ver, yorum satırı yazma.
                """.formatted(subject, repositoryAnswer);
    }

    @Override
    public void parseAnswer(String answer) {
        defaultParseAnswer(answer, "services");
    }
}
