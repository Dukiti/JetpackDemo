package com.example.dungnm.convention.utils;

import org.gradle.api.Project;
import org.gradle.api.artifacts.VersionCatalog;
import org.gradle.api.artifacts.VersionCatalogsExtension;

public class ProjectUtils {

    public static VersionCatalog getVersionCatalog(Project project) {
        return project.getExtensions().getByType(VersionCatalogsExtension.class).named("libs");
    }
}
