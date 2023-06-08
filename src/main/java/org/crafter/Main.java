package org.crafter;

import org.crafter.adapters.OpenAIAdapter;
import picocli.CommandLine;

public class Main {

    public static void main(String[] args) {

        OpenAIAdapter openAIAdapter = new OpenAIAdapter();
        new CommandLine(new ProjectStarter(openAIAdapter))
                .execute("-p", "my-project", "-t", "java", "-d", "project", "-s", "e ticaret projesi");
    }
}
