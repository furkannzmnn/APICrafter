package org.crafter.templates;

public interface ApplicationTemplate {
    String prompt(String subject);

    void parseAnswer(String answer);
}
