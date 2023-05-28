package org.crafter;

import org.crafter.adapters.OpenAIAdapter;
import picocli.CommandLine;

import java.util.concurrent.Callable;

public class ProjectStarter implements Callable<Integer> {

    private final OpenAIAdapter openAIAdapter;

    public ProjectStarter(OpenAIAdapter openAIAdapter) {
        this.openAIAdapter = openAIAdapter;
    }

    @CommandLine.Option(names = {"-p", "--project"}, description = "Project name", required = true)
    private String projectName;

    @CommandLine.Option(
            names = {"-t", "--type"},
            description = "Project type",
            required = true,
            help = true,
            paramLabel = "<project-type>",
            defaultValue = "java"
    )
    private String projectType;

    @CommandLine.Option(
            names = {"-d", "--directory"},
            description = "Project directory",
            required = true,
            help = true,
            paramLabel = "<project-directory>",
            defaultValue = "."
    )
    private String projectDirectory;

    @CommandLine.Option(
            names = {"-s", "--subject"}
    )
    private String subject;

    @Override
    public Integer call() {
        openAIAdapter.createProject(subject);
        return 1;
    }
}
