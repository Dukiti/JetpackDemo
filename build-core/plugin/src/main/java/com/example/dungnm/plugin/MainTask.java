package com.example.dungnm.plugin;

import static org.codehaus.groovy.runtime.DefaultGroovyMethods.println;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

public class MainTask extends DefaultTask {
    @TaskAction
    public void verify() {
        println("APPLY FUNCTION HAS BEEN ENTERED");
    }
}
