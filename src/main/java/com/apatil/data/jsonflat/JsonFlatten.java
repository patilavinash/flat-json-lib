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
import java.io.Console;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

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

//        if (args == null || args.length == 0) {
//            throw new IllegalArgumentException("Please pass atleast 1 json string as argument");
//        }
        StringBuilder inputBuilder = new StringBuilder();
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()) {
            String next = scanner.nextLine();
            inputBuilder.append(next);
            if (next.isEmpty()) {
                break;
            }
        }

        //System.out.println(" in ->  " + inputBuilder.toString());
        String input = inputBuilder.toString();
        if (input == null || input.isEmpty()) {
            throw new IllegalArgumentException("Please pass valid json as input");
        }
        JsonNode flattenJson = new JsonFlatten().flattenJson(input);
        System.out.println(flattenJson.toString());
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

    private void traverseJson(final JsonNode node, final List<String> currentPath, final ObjectNode output) {

        Iterator<Map.Entry<String, JsonNode>> children = node.fields();
        while (children.hasNext()) {
            Map.Entry<String, JsonNode> currNode = children.next();
            final String key = currNode.getKey();
            final JsonNode value = currNode.getValue();
            currentPath.add(key);
            if (value.isContainerNode() && !value.isEmpty(null)) { // check for empty json objects {}
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
