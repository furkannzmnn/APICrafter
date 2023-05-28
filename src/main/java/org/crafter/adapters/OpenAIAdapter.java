package org.crafter.adapters;

import com.theokanning.openai.completion.CompletionChoice;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.completion.CompletionResult;
import com.theokanning.openai.service.OpenAiService;
import org.crafter.templates.*;

import java.util.List;
import java.util.stream.Collectors;

public class OpenAIAdapter {

    private final OpenAiService service;
    private final List<ApplicationTemplate> applicationTemplates;
    private final String OPEN_API_KEY;



    public OpenAIAdapter() {
        OPEN_API_KEY = System.getenv("OPEN_API_KEY");
        service = new OpenAiService("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");
        this.applicationTemplates = List.of(
                new ServiceTemplate(),
                new ControllerTemplate(),
                new RepositoryTemplate(),
                new ModelTemplate()
        );
    }

    public void createProject(String projectName) {
        // layerlar arası modeller farklı olabilir, o yüzden her soru sorulduğunda önceki bilgiyi sonraki soruya taşı
        for (ApplicationTemplate applicationTemplate : applicationTemplates) {
            String prompt = applicationTemplate.prompt(projectName);
            CompletionRequest request = new CompletionRequest();
            request.setPrompt(prompt);
            request.setMaxTokens(1000);
            request.setTemperature(0.9);
            request.setModel("text-davinci-003");
            CompletionResult result = service.createCompletion(request);
            String answer = result.getChoices().stream().map(CompletionChoice::getText).collect(Collectors.joining());
            applicationTemplate.parseAnswer(answer);
        }
    }
}
