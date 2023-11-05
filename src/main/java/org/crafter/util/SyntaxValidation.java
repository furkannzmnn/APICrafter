package org.crafter.util;

public final class SyntaxValidation {

    public static String packageAndImportExecute(String packageName, String importName, String classContent) {
        String[] lines = classContent.split("\n");
        StringBuilder classStrBuilder = new StringBuilder();
        boolean isPackageAdded = false;
        boolean isImportAdded = false;
        for (String line : lines) {
            if (!isPackageAdded && line.startsWith("package")) {
                classStrBuilder.append("package ").append(packageName).append(";\n");
                isPackageAdded = true;
            } else if (!isImportAdded && line.startsWith("import")) {
                classStrBuilder.append("import ").append(importName).append(";\n");
                isImportAdded = true;
            } else {
                classStrBuilder.append(line).append("\n");
            }
        }
        return classStrBuilder.toString();
    }
}
