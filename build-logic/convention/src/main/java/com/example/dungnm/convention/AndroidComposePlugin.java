package com.example.dungnm.convention;

import com.android.build.gradle.BaseExtension;
import com.example.dungnm.convention.utils.ProjectUtils;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.VersionCatalog;
import org.gradle.api.artifacts.dsl.DependencyHandler;

public class AndroidComposePlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        VersionCatalog libs = ProjectUtils.getVersionCatalog(project);
        BaseExtension baseExtension = project.getExtensions().getByType(BaseExtension.class);
        baseExtension.getBuildFeatures().setCompose(true);
        baseExtension.composeOptions(composeOptions -> {
            String kotlinCompilerExtVer = libs.findVersion("androidxComposeCompiler").get().toString();
            composeOptions.setKotlinCompilerExtensionVersion(kotlinCompilerExtVer);
        });
        DependencyHandler dependencyHandler = project.getDependencies();
        dependencyHandler.add("implementation", dependencyHandler.platform(libs.findLibrary("androidx-compose-bom").get()));
    }
}
