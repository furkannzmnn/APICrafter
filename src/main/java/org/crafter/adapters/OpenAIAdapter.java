package org.crafter.adapters;

import com.theokanning.openai.completion.CompletionChoice;
import com.theokanning.openai.completion.CompletionChunk;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.completion.CompletionResult;
import com.theokanning.openai.service.OpenAiService;
import io.reactivex.Flowable;
import org.crafter.templates.*;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class OpenAIAdapter {

    public static final ConcurrentHashMap<String, String> ANSWERS = new ConcurrentHashMap<>();

    private final OpenAiService service;
    private final List<ApplicationTemplate> applicationTemplates;
    private final String OPEN_API_KEY;


    public OpenAIAdapter() {
        OPEN_API_KEY = System.getenv("OPEN_API_KEY");
        service = new OpenAiService("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", Duration.of(30, java.time.temporal.ChronoUnit.SECONDS));
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
