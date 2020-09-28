/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apatil.data.jsonflat;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Avinash Patil
 */
public class JsonFlatten {

    private String delimiter;
    public static final String DEFAULT_DELIMITER = ".";
    public static final ObjectMapper MAPPER = new ObjectMapper();

    public JsonFlatten() {
        delimiter = DEFAULT_DELIMITER;
    }

    public JsonFlatten(String delimiter) {
        this.delimiter = delimiter;
    }

    public static void main(String[] args) {

        if (args == null || args.length == 0) {
            throw new IllegalArgumentException("Please pass atleast 1 json string as argument");
        }

        String input = args[0];
        JsonNode flattenJson = new JsonFlatten().flattenJson(input);
        System.out.println( flattenJson.toString() );        
    }
    
    public JsonNode flattenJson(final String json) {

        if (json == null || json.isEmpty()) {
            throw new IllegalArgumentException("Input string cannot be empty or null");
        }

        JsonNode jsonNode;

        try {
            jsonNode = MAPPER.readTree(json);
        } catch (IOException ex) {
            throw new IllegalArgumentException("Error parsing Input Json ", ex);
        }

        ObjectNode output = new ObjectNode(JsonNodeFactory.instance);
        List<String> path = new ArrayList<>();
        traverseJson(jsonNode, path, output);

        return output;
    }

    private void traverseJson(final JsonNode node,final List<String> currentPath,final ObjectNode output) {

        Iterator<Map.Entry<String, JsonNode>> fieldsIterator = node.fields();
        while (fieldsIterator.hasNext()) {
            Map.Entry<String, JsonNode> field = fieldsIterator.next();
            final String key = field.getKey();
            final JsonNode value = field.getValue();
            currentPath.add(key);
            if (value.isContainerNode() && !value.isEmpty(null)) {
                    traverseJson(value, currentPath, output);
            } else {
                output.set(getCurrentKeyPath(currentPath), value);
            }
            currentPath.remove(currentPath.size() - 1);
        }
    }

    private String getCurrentKeyPath(final List<String> currentKeyPath) {
        return String.join(delimiter, currentKeyPath);
    }

}
