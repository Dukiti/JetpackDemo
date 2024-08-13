package com.example.dungnm.convention.utils;

import org.gradle.api.Project;
import org.gradle.api.artifacts.VersionCatalog;
import org.gradle.api.artifacts.VersionCatalogsExtension;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProjectUtils {

    public static VersionCatalog getVersionCatalog(Project project) {
        return project.getExtensions().getByType(VersionCatalogsExtension.class).named("libs");
    }

    public static String getCurrentFlavor(Project project) {
        String taskRequestsStr = project.getGradle().getStartParameter().getTaskRequests().toString();
        Pattern pattern = null;
        if (taskRequestsStr.contains("assemble")) {
            pattern = Pattern.compile("assemble(\\w+)(Release|Debug)");
        } else {
            pattern = Pattern.compile("bundle(\\w+)(Release|Debug)");
        }
        Matcher matcher = pattern.matcher(taskRequestsStr);
        String flavor = "";
        if (matcher.find()) {
            flavor = matcher.group(1).toLowerCase(Locale.getDefault());
        }
        return flavor;
    }

    public static Properties getLocalProperties(Project project) throws IOException {
        File file = project.getRootProject().file("local.properties");
        Properties properties = new Properties();
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            properties.load(inputStream);
            return properties;
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }
}
