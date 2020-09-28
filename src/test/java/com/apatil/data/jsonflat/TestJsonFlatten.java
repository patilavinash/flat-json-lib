/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apatil.data.jsonflat;

import com.apatil.data.jsonflat.JsonFlatten;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Avinash Patil
 */
public class TestJsonFlatten {
    
    @Test
    public void testJsonFlatten(){
        String json = "{\n"
                + "    \"a\": 1,\n"
                + "    \"b\": true,\n"
                + "    \"c\": {\n"
                + "        \"d\": 3,\n"
                + "        \"e\": \"test\"\n"
                + "    }\n"
                + "}";
        
        ObjectNode output = new ObjectNode(JsonNodeFactory.instance);
        output.put("a", 1);
        output.put("b", true);
        output.put("c.d", 3);
        output.put("c.e", "test");
        
        Assert.assertEquals(output, new JsonFlatten().flattenJson(json));
        
    }
    
    @Test
    public void testSimpleJson(){
        String json = "{\n"
                + "    \"a\": {},\n"
                + "    \"b\": true\n"
                + "}";
        
        ObjectNode output = new ObjectNode(JsonNodeFactory.instance);
        output.put("a", new  ObjectNode(JsonNodeFactory.instance));
        output.put("b", true);
        
        Assert.assertEquals(output, new JsonFlatten().flattenJson(json));        
    }
    
    @Test
    public void testMultiLevelFlatten(){
        String json = "{\n"
                + "    \"a\": {\n"
                + "        \"a\":  {\n"
                + "			\"a\": {\n"
                + "					\"a\": {\n"
                + "						\"a\": \"multi-level\"\n"
                + "					}\n"
                + "				}\n"
                + "			}\n"
                + "    }\n"
                + "}";
        
        
        
        ObjectNode output = new ObjectNode(JsonNodeFactory.instance);
        output.put("a.a.a.a.a", "multi-level");
        
        Assert.assertEquals(output, new JsonFlatten().flattenJson(json));
        
    }
    
    
    @Test
    public void testEmptyValueException(){
        String json = "";
        
        Exception exception = Assert.assertThrows(RuntimeException.class, () -> {
             new JsonFlatten().flattenJson(json);
        });
        
        String expectedMessage = "Input string cannot be empty or null";
        String message = exception.getMessage();
        
        Assert.assertEquals(expectedMessage,message);
    }
    
    
    @Test
    public void testInvalidJsonException(){
        String json = "{ \"a\":\'hey\" ";
        
        Exception exception = Assert.assertThrows(RuntimeException.class, () -> {
             new JsonFlatten().flattenJson(json);
        });
        
        String expectedMessage = "Error parsing Input Json ";
        String message = exception.getMessage();
        
        Assert.assertEquals(expectedMessage,message);
    }
    
}
