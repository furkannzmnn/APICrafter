package org.crafter;

import org.crafter.adapters.OpenAIAdapter;
import org.crafter.util.BaseDirectoryCreator;
import picocli.CommandLine;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;

public class ProjectStarter implements Callable<Integer> {
    public static final ConcurrentHashMap<String, String> PROJECT_INFO = new ConcurrentHashMap<>();

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
        PROJECT_INFO.put("projectName", projectName);
        PROJECT_INFO.put("projectType", projectType);
        PROJECT_INFO.put("projectDirectory", BaseDirectoryCreator.createBaseDirectory(projectDirectory));
        openAIAdapter.createProject(subject);
        return 1;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getProjectType() {
        return projectType;
    }

    public String getProjectDirectory() {
        return projectDirectory;
    }

    public String getSubject() {
        return subject;
    }
}
