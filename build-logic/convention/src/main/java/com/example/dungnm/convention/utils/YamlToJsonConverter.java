package com.example.dungnm.convention.utils;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;

public class YamlToJsonConverter {
    public static String convertYamlToJson(String yaml) throws IOException {
        ObjectMapper yamlReader = new ObjectMapper(new YAMLFactory());
        // Read YAML string as JsonNode
        JsonNode yamlNode = yamlReader.readTree(yaml);

        // Create ObjectMapper for JSON writing
        ObjectMapper jsonWriter = new ObjectMapper();
        // Convert JsonNode to JSON string
        return jsonWriter.writeValueAsString(yamlNode);
    }
    public static String convertYamlToJson(File yaml) throws IOException {
        ObjectMapper yamlReader = new ObjectMapper(new YAMLFactory());
        // Read YAML string as JsonNode
        JsonNode yamlNode = yamlReader.readTree(yaml);

        // Create ObjectMapper for JSON writing
        ObjectMapper jsonWriter = new ObjectMapper();
        // Convert JsonNode to JSON string
        return jsonWriter.writeValueAsString(yamlNode);
    }
}
