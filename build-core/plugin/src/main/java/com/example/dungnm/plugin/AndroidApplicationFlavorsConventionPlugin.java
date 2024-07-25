package com.example.dungnm.plugin;

import com.android.build.gradle.BaseExtension;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class AndroidApplicationFlavorsConventionPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        project.getPlugins().withId("com.android.application", plugin -> {
            BaseExtension android = project.getExtensions().getByType(BaseExtension.class);

            android.getProductFlavors().create("demo", flavor -> {
//                flavor.setApplicationId("com.example.demo");
            });

            android.getProductFlavors().create("full", flavor -> {
//                flavor.setApplicationId("com.example.full");
            });
        });
        ;
    }
}
