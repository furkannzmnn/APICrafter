package org.crafter.adapters;

import com.theokanning.openai.completion.CompletionChoice;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.completion.CompletionResult;
import com.theokanning.openai.service.OpenAiService;
import org.crafter.IoContainer;
import org.crafter.templates.ApplicationTemplate;
import org.crafter.templates.PostCreateAction;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;


public class OpenAIAdapter {
    private final OpenAiService service;
    private final List<ApplicationTemplate> applicationTemplates = new CopyOnWriteArrayList<>();
    private final List<PostCreateAction> postCreateActions = new CopyOnWriteArrayList<>();

    public OpenAIAdapter() {
        service = new OpenAiService("", Duration.of(5, ChronoUnit.MINUTES));
        IoContainer.fillBeanInIoContainer(
                Map.of("applicationTemplates", applicationTemplates, "postCreateActions", postCreateActions)
        );
    }

    public void createProject(String subject) {
        for (ApplicationTemplate applicationTemplate : applicationTemplates) {
            String prompt = applicationTemplate.prompt(subject);
            CompletionRequest request = new CompletionRequest();
            request.setPrompt(prompt);
            request.setMaxTokens(1000);
            request.setTemperature(0.9);
            request.setModel("text-davinci-003");
            CompletionResult result = service.createCompletion(request);
            String answer = result.getChoices().stream().map(CompletionChoice::getText).collect(Collectors.joining());
            applicationTemplate.parseAnswer(answer);
        }
        postCreateActions.forEach(PostCreateAction::execute);
    }
}
