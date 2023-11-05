package org.crafter.templates.git;

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
