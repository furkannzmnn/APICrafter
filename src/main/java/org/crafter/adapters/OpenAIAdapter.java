package org.crafter.adapters;

import com.theokanning.openai.completion.CompletionChoice;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.completion.CompletionResult;
import com.theokanning.openai.service.OpenAiService;
import org.crafter.templates.*;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class OpenAIAdapter {
    public static final ConcurrentHashMap<String, String> ANSWERS = new ConcurrentHashMap<>();

    private final OpenAiService service;
    private final List<ApplicationTemplate> applicationTemplates;

    public OpenAIAdapter() {
        service = new OpenAiService("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", Duration.of(5, ChronoUnit.MINUTES));
        this.applicationTemplates = List.of(
                new ModelTemplate(),
                new RepositoryTemplate(),
                new ServiceTemplate(),
                new ControllerTemplate()
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
    }
}
