package com.example.dungnm.convention;

import com.android.build.gradle.BaseExtension;
import com.example.dungnm.convention.config.AppFlavor;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

import java.util.List;

public class AppFlavorsConventionPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        BaseExtension android = project.getExtensions().getByType(BaseExtension.class);
        android.flavorDimensions(BaseConfig.DIMENSION_DEFAULT);
        for (int i = 0; i < AppFlavor.values().length; i++) {
            AppFlavor appFlavor = AppFlavor.values()[i];
            String flavorName = appFlavor.name().toLowerCase();
            android.getProductFlavors().create(flavorName, flavor -> {
                flavor.set_dimension(BaseConfig.DIMENSION_DEFAULT);
            });
            android.getSourceSets().maybeCreate(flavorName).getJava().setSrcDirs(List.of("src/common/java"));
        }
    }
}
