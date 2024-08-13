package com.example.dungnm.convention;

import com.android.build.api.variant.AndroidComponentsExtension;
import com.android.build.gradle.BaseExtension;
import com.example.dungnm.convention.model.FieldType;
import com.example.dungnm.convention.utils.ProjectUtils;
import com.example.dungnm.convention.utils.YamlToJsonConverter;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.squareup.javapoet.ArrayTypeName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import org.gradle.api.DefaultTask;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.tasks.TaskAction;
import org.gradle.api.tasks.TaskProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.lang.model.element.Modifier;

public class NetworkConfigPlugin implements Plugin<Project> {

    private final static String CLASS_NAME = "NetworkConfig";
    private final static String OUTPUT_PATH = "generated/networkConfig/main/java";

    @Override
    public void apply(Project project) {
        project.afterEvaluate(project1 -> {
            File buildDir = project.getLayout().getBuildDirectory().get().getAsFile();
            File dirDes = new File(buildDir, OUTPUT_PATH);
            BaseExtension baseExtension = project.getExtensions().getByType(BaseExtension.class);
            baseExtension.getSourceSets().getByName("main").getJava().setSrcDirs(List.of(dirDes.getPath()));
        });

        TaskProvider<GenerateCodeTask> generateCodeTask = project.getTasks().register("generateNetworkConfig", GenerateCodeTask.class);
        AndroidComponentsExtension<?, ?, ?> androidComponents = project.getExtensions().getByType(AndroidComponentsExtension.class);

        project.getTasks().named("preBuild").configure(task -> {
            task.dependsOn(generateCodeTask);
        });
    }


    private String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public static class GenerateCodeTask extends DefaultTask {
        private Map<String, FieldType> mapField = Map.of(
                "DEBUG_MODE", FieldType.BOOLEAN,
                "TIME_OUT", FieldType.LONG,
                "MAX_REQUEST", FieldType.INT,
                "BASE_URL", FieldType.STRING,
                "NET_WORK_CODE_SUCCESS", FieldType.LIST);

        private String nameSpace;
        private File buildDir;
        private String currentFlavor;
        private Properties networkProperties;

        public GenerateCodeTask() throws IOException {
            nameSpace = getProject().getExtensions().getByType(BaseExtension.class).getNamespace();
            buildDir = getProject().getLayout().getBuildDirectory().getAsFile().get();
            currentFlavor = ProjectUtils.getCurrentFlavor(getProject());
            networkProperties = getNetworkProperties();
        }


        @TaskAction
        public void generate() {
            generateFromProperties();
        }

        private void generateFromProperties() {
            try {
                TypeSpec.Builder builder = TypeSpec.classBuilder(CLASS_NAME).addModifiers(Modifier.PUBLIC, Modifier.FINAL);
                mapField.forEach((key, type) -> {
                    TypeName typeName = getTypeName(type);
                    CodeBlock codeBlock = generateCodeBlock(type, networkProperties.getProperty(key));
                    FieldSpec field = FieldSpec.builder(typeName, key).addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL).initializer(codeBlock).build();
                    builder.addField(field);
                });
                JavaFile javaFile = JavaFile.builder(nameSpace, builder.build()).build();
                File createDir = new File(buildDir, OUTPUT_PATH);
                if (!createDir.exists()) {
                    createDir.mkdir();
                }
                javaFile.writeTo(createDir);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        private Properties getNetworkProperties() throws IOException {
            InputStream inputStream = null;
            try {
                Properties localProperties = ProjectUtils.getLocalProperties(getProject());
                String path = localProperties.getProperty("NETWORK_CONFIG_PATH");
                File file = getProject().getRootProject().file(path + "\\" + currentFlavor + "\\network.properties");
                inputStream = new FileInputStream(file);
                Properties networkProperties = new Properties();
                networkProperties.load(inputStream);
                return networkProperties;
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
            }
        }

        private TypeName getTypeName(FieldType type) {
            Map<FieldType, TypeName> map = Map.of(FieldType.BOOLEAN, TypeName.BOOLEAN,
                    FieldType.LONG, TypeName.LONG,
                    FieldType.INT, TypeName.INT,
                    FieldType.STRING, TypeName.get(String.class),
                    FieldType.LIST, ArrayTypeName.of(String.class));
            return map.get(type);
        }

        private CodeBlock generateCodeBlock(FieldType type, String value) {
            Map<FieldType, CodeBlock> map = Map.of(FieldType.STRING, CodeBlock.of("$S", value), FieldType.BOOLEAN, CodeBlock.of("$L", value), FieldType.LONG, CodeBlock.of("$L", value), FieldType.INT, CodeBlock.of("$L", value), FieldType.LIST, CodeBlock.of("$S.split($S)", value, ","));
            return map.get(type);
        }

        private FieldSpec getFieldByName(Properties properties, String name, String configName) {
            return FieldSpec.builder(String.class, name).addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL).initializer("$S", properties.get(configName)).build();
        }

        private void readFromYaml() {
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


    }
}
