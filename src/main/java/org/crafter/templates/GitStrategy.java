package org.crafter.templates;

public interface GitStrategy {
    void gitInit();
    void gitAdd();
    void gitCommit();
    void gitPush();

    default void executeGitOperations() {
        gitInit();
        gitAdd();
        gitCommit();
        gitPush();
    }
}
