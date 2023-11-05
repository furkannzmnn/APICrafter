package org.crafter.templates.business;

import org.crafter.Bean;

import java.util.List;

import static java.lang.StringTemplate.STR;
import static org.crafter.ProjectStarter.ANSWERS;

@Bean(name = "applicationTemplates")
public class ControllerTemplate implements ApplicationTemplate{
    @Override
    public String prompt(String subject) {
        return STR. """
                \{subject} konulu bir proje yazıyorum spring boot ile. Bana bu projede olması gereken controller sınıfları
                yazar mısın ? Yazarken sadece java sınıf kodları yaz.
                Servis sınıfları aşşağıdaki gibidir
                \{ANSWERS.get(ServiceTemplate.class.getSimpleName())}

                Örnek bir controller sınıfı:
                ```java
package org.crafter.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

// Örnek bir controller sınıfı
@RestController
@RequestMapping("/sample")
public class SampleController {
    @Autowired
    private final SampleService service;

    public SampleController(SampleService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public SampleDTO getSample(@PathVariable Long id) {
        // Controller içeriği: Service'e yönlendirme yapılır
        return service.getSampleById(id);
    }

    // ... diğer endpointler ...
}

Yukarıdaki örnekte olduğu gibi, projede bulunması gereken controller sınıflarını ayrı ayrı tanımlayarak gönderir misiniz? Her controller sınıfı için ayrı bir blok şeklinde kod örneği bekliyorum. Ayrıca, controller sınıflarının belirli bir service ile ilişkili olması gerektiğini unutmayınız. Bu ilişkiyi kurarak ilgili service'i controller sınıfına eklemeyi unutmayınız.
                """;

    }

    @Override
    public void parseAnswer(String answer) {
      defaultParseAnswer(answer, "controllers");
    }

    @Override
    public Integer order() {
        return 3;
    }
}
