/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mongo.data.jsonflat;

import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
 
import java.util.Arrays;
import java.util.Collection;
import org.junit.Assert;
import static org.mongo.data.jsonflat.JsonFlatten.MAPPER;
 

/**
 *
 * @author Avinash Patil
 */
@RunWith(Parameterized.class)
public class TestMultipleJsons {
    
    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { "{\"key\":\"value\"}", "{\"key\":\"value\"}" } ,
                { "{\"a\": { \"a\":  {	\"a\": { \"a\": { \"a\": \"multi-level\"}}}}}" , "{\"a.a.a.a.a\":\"multi-level\"}" },
                { "{\"a\": { \"a\":  {	\"a\": { \"a\": { \"a\": \"multi-level\"}}}}}" , "{\"a.a.a.a.a\":\"multi-level\"}" },
                { "{\"a\": { \"b\" : { \"a\" :\"abc\"} } , \"b\" : { \"a\" :  { \"a\" :\"abc\"} }  } " , "{\"a.b.a\" : \"abc\" ,\"b.a.a\" : \"abc\" }" },                
                { "{\"a\": { \"b\" : { } }, \"b\" : { \"a\" :  { \"a\" :\"abc\"} }  } " , "{\"a.b\" : {} ,\"b.a.a\" : \"abc\" }" },
                { "{}" , "{}" }
        });
    }
    private final String input;
    private final String output;
    public TestMultipleJsons(String input ,String expected) {
        this.input = input;
        this.output = expected;
    }
    
    @Test
    public void testAllValidJsons() {
         
        JsonNode outputNode;
        
        try {
            outputNode = MAPPER.readTree(output);
        } catch( IOException ex) {
           throw new IllegalArgumentException("Error parsing Input Json ",ex); 
        }
        
        Assert.assertEquals(outputNode, new JsonFlatten().flattenJson(input));     
    }
    
}
