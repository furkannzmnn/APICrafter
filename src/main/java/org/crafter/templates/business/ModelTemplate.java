package org.crafter.templates.business;

import org.crafter.Bean;
import org.crafter.ProjectStarter;

import java.util.List;

@Bean(name = "applicationTemplates")
public class ModelTemplate implements ApplicationTemplate{
    @Override
    public String prompt(String subject) {
        return STR."""
                \{subject} konulu bir Spring Boot projesi geliştiriyorum. Bu projede kullanılacak olan model sınıflarını ve bu sınıfların sahip olması gereken alanları aşağıda belirtiyorum:

// Örnek bir model sınıfı
package org.crafter.models;
import lombok.Data;
@Data
public class SampleModel {
    private String field1;
    private int field2;
    // ... diğer alanlar ...
}

        Yukarıdaki örnekte olduğu gibi, projede bulunması gereken model sınıflarını ayrı ayrı tanımlayarak gönderir misiniz? Her model sınıfı için ayrı bir blok şeklinde kod örneği bekliyorum.

                """;
    }

    @Override
    public void parseAnswer(String answer) {
        List<String> classes = defaultParseAnswer(answer, "models");
        ProjectStarter.ANSWERS.put("ModelTemplate", String.join("\n\n", classes));
    }

    @Override
    public Integer order() {
        return 0;
    }
}
