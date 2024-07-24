//package com.example.dungnm.plugin
//
//import org.gradle.api.Plugin
//import org.gradle.api.Project
//
//class TemplatePlugin : Plugin<Project> {
//
//    private val EXTENSION_NAME = "templateExampleConfig"
//    private val TASK_NAME = "templateExample"
//
//    override fun apply(project: Project) {
//        // Add the 'template' extension object
//        val extension =
//            project.extensions.create(EXTENSION_NAME, TemplateExtension::class.java, project)
//
//        // Add a task that uses configuration from the extension object
//        project.tasks.register(TASK_NAME, TemplateExampleTask::class.java) {
//            it.tag.set(extension.tag)
//            it.message.set(extension.message)
//            it.outputFile.set(extension.outputFile)
//        }
//    }
//}
