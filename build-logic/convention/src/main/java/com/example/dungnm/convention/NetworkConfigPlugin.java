package com.example.dungnm.convention;

import com.android.build.api.variant.AndroidComponentsExtension;
import com.android.build.gradle.api.BaseVariant;
import com.example.dungnm.convention.utils.YamlToJsonConverter;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import org.gradle.api.DefaultTask;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.tasks.TaskAction;
import org.gradle.internal.impldep.com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.lang.model.element.Modifier;

public class NetworkConfigPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        Task generateCodeTask = project.getTasks().create("generateNetworkConfig", GenerateCodeTask.class);
        AndroidComponentsExtension<?, ?, ?> androidComponents = project.getExtensions().getByType(AndroidComponentsExtension.class);

        // Register a callback to configure variants
        androidComponents.onVariants(androidComponents.selector().all(), variant -> {
            String variantName = variant.getName();
            // Get the variant's assemble task name and hook the custom task to it
            String assembleTaskName = "assemble" + capitalize(variantName);

            project.afterEvaluate(p -> {
                Task assembleTask = project.getTasks().findByName(assembleTaskName);
                if (assembleTask != null) {
                    assembleTask.finalizedBy(generateCodeTask);
                }
            });
        });

//        project.getPlugins().all(plugin -> {
//            if (plugin instanceof AppPlugin) {
//                project.getExtensions().getByType(AppExtension.class).getApplicationVariants().all(variant -> {
//                    variant.getAssembleProvider().configure(assembleTask -> {
//                        finalizedBy(variant, generateCodeTask);
//                    });
//                });
//            } else if (plugin instanceof LibraryPlugin) {
//                project.getExtensions().getByType(LibraryExtension.class).getLibraryVariants().all(variant -> {
//                    finalizedBy(variant, generateCodeTask);
//                });
//            }
//        });
//        AppExtension android = project.getExtensions().getByType(AppExtension.class);
//        android.getApplicationVariants().all(variant -> {
//            variant.getAssembleProvider().configure(assembleTask -> {
//                assembleTask.finalizedBy("generateNetworkConfig");
//            });
//        });
    }


    private void finalizedBy(BaseVariant variant, Task task) {
        variant.getAssembleProvider().configure(assembleTask -> {
            assembleTask.finalizedBy(task);
        });
    }

    private String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public static class GenerateCodeTask extends DefaultTask {
        @TaskAction
        public void generate() {
            try {
                Project project = getProject();
                File file = new File(project.getRootDir(), "network.yaml");
                String jsonData = YamlToJsonConverter.convertYamlToJson(file);
                JsonObject jsonObject = (new Gson()).fromJson(jsonData, JsonObject.class);
                JsonArray networkArr = jsonObject.getAsJsonArray("networks");
                JsonObject data = null;
                for (int i = 0; i < networkArr.size(); i++) {
                    JsonObject jsonVariant = networkArr.get(i).getAsJsonObject();
                    if (jsonVariant.has("dev")) {
                        data = jsonVariant.getAsJsonObject("dev");
                    }
                }
                if (data == null) {
                    throw new RuntimeException("not found config for current variant");
                }
                FieldSpec.Builder builder = FieldSpec.builder(String.class, "BASE_URL").addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL);
                String baseUrlValue = data.get("baseUrl").toString();
                builder.initializer(baseUrlValue);
                TypeSpec.Builder typeBuilder = TypeSpec.classBuilder("NetworkConfig").addModifiers(Modifier.PUBLIC, Modifier.FINAL).addField(builder.build());
                JavaFile javaFile = JavaFile.builder(project.getName(), typeBuilder.build()).build();
                File buildDir = project.getLayout().getBuildDirectory().get().getAsFile();
                File createFile = new File(buildDir, "generated-sources/network/");
                if (!createFile.exists()) {
                    createFile.mkdirs();
                }
                javaFile.writeTo(createFile);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        private void writeFromProperties(Project project) {
            FileInputStream inputStream = null;
            try {
                File file = new File("network.properties");
                inputStream = new FileInputStream(file);
                Properties properties = new Properties();
                properties.load(inputStream);
                TypeSpec.Builder builder = TypeSpec.classBuilder("NetworkConfig").addModifiers(Modifier.PUBLIC, Modifier.FINAL).addField(getFieldByName(properties, "BASE_URL", "BASE_URL"));
                JavaFile javaFile = JavaFile.builder(project.getName(), builder.build()).build();
                File createFile = new File(project.getRootDir(), "src/main/java");
                if (!createFile.exists()) {
                    createFile.mkdirs();
                }
                javaFile.writeTo(createFile);
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }

        private FieldSpec getFieldByName(Properties properties, String name, String configName) {
            return FieldSpec.builder(String.class, name).addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL).initializer("$S", properties.get(configName)).build();
        }

        String convertYamlToJson(File yaml) throws Exception {
            ObjectMapper yamlReader = new ObjectMapper();
            Object obj = yamlReader.readValue(yaml, Object.class);

            ObjectMapper jsonWriter = new ObjectMapper();
            return jsonWriter.writeValueAsString(obj);
        }

    }
}
