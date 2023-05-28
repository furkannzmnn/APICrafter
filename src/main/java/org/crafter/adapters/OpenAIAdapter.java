package org.crafter.adapters;

public class OpenAIAdapter {
    public static String getOpenAIKey() {
        return System.getenv("OPENAI_API_KEY");
    }
}
