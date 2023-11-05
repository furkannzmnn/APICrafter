package org.crafter.util;

import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

import static org.crafter.util.SyntaxValidation.packageAndImportExecute;

public final class ClassValidation {
    public static boolean isValidClassName(String classContent) {
        String[] lines = classContent.split("\n");
        for (String line : lines) {
            if (line.startsWith("public class") || line.startsWith("public interface")) {
                return true;
            }
        }
        return false;
    }

    public static String fixClassContentIfContentInvalid(String classContent) {
        StringBuilder fixedContent = new StringBuilder();

        boolean isInComment = false;
        boolean isInString = false;
        boolean isInChar = false;

        for (char c : classContent.toCharArray()) {
            if (c == '/' && !isInString && !isInChar) {
                isInComment = true;
            } else if (c == '*' && isInComment && !isInString && !isInChar) {
                isInComment = false;
            } else if (c == '\"' && !isInComment && !isInChar) {
                isInString = !isInString;
            } else if (c == '\'' && !isInComment && !isInString) {
                isInChar = !isInChar;
            }

            if (!isInComment && !isInString && !isInChar) {
                fixedContent.append(c);
            }
        }

        return fixedContent.toString();
    }


    public static List<String> clearComments(List<String> classStrings) {
        classStrings = classStrings.stream().map(classStr -> {
            String[] lines1 = classStr.split("\n");
            StringBuilder classStrBuilder = new StringBuilder();
            for (String line : lines1) {
                if (!line.trim().startsWith("//")) {
                    classStrBuilder.append(line).append("\n");
                }
            }
            return classStrBuilder.toString();
        }).toList();
        return classStrings;
    }

    public static List<String> extractClassStrings(String response) {

        List<String> classStrings = new ArrayList<>();
        String[] lines = response.split("\n");

        StringBuilder classString = new StringBuilder();

        for (String line : lines) {
            // package ve import satırlarını da alalım, package en başta olmalı sonra importlar olmalı
            if (line.startsWith("package") || line.startsWith("import")) {
                classString.append(line).append("\n");
            } else if (line.startsWith("public class") || line.startsWith("public interface")) {
                if (classString.length() > 0) {
                    classStrings.add(classString.toString());
                }
                classString = new StringBuilder();
                classString.append(line).append("\n");
            } else {
                classString.append(line).append("\n");
            }
        }

        if (classString.length() > 0) {
            classStrings.add(classString.toString());
        }


        classStrings = clearComments(classStrings);

        return classStrings;
    }

}
