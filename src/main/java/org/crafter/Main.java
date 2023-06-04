package org.crafter;

import org.crafter.adapters.OpenAIAdapter;
import picocli.CommandLine;

public class Main {
    public static void main(String[] args) {
        OpenAIAdapter openAIAdapter = new OpenAIAdapter();


        int execute = new CommandLine(new ProjectStarter(openAIAdapter))
                .execute("-p", "my-project", "-t", "java", "-d", "my-project-dir", "-i", "e ticaret");
    }
}
