package org.crafter.templates;

import static org.crafter.ProjectStarter.PROJECT_INFO;

public class GitPushService implements GitStrategy, PostCreateAction {

    private final String projectPath;
    private final String gitUrl;

    public GitPushService(String projectPath, String gitUrl) {
        this.projectPath = projectPath;
        this.gitUrl = gitUrl;
    }

    public GitPushService() {
        this.projectPath = PROJECT_INFO.get("projectDirectory");
        this.gitUrl = null;
    }

    @Override
    public void gitInit() {
        System.out.println("Git repository is initializing...");
        try {
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.command("bash", "-c", "cd " + projectPath + " && git init");
            Process process = processBuilder.start();
            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void gitAdd() {
        System.out.println("Git add is executing...");
        try {
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.command("bash", "-c", "cd " + projectPath + " && git add .");
            Process process = processBuilder.start();
            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void gitCommit() {
        System.out.println("Git commit is executing...");
        try {
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.command("bash", "-c", "cd " + projectPath + " && git commit -m \"Initial commit\"");
            Process process = processBuilder.start();
            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void gitPush() {
        System.out.println("Git push is executing...");
        try {
            ProcessBuilder processBuilder = new ProcessBuilder();
            String gitBranch = "main";
            processBuilder.command("bash", "-c", "cd " + projectPath + " && git push " + gitUrl + " " + gitBranch);
            Process process = processBuilder.start();
            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void execute() {
        executeGitOperations();
    }
}
