package com.example.dungnm.plugin;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class MainPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        project.getTasks().register("demoTask", MainTask.class);
    }
}
