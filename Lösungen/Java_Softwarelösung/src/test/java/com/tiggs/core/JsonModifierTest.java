package com.tiggs.core;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonModifierTest {

    @Test
    public void addTimestampToJsonTest() {
        JsonModifier jsonModifier = new JsonModifier();
        String json = jsonModifier.addTimestampToJson("{ name: \"Jamshedi\", age: 23, city: \"Frankfurt am Main\" }");
        JSONObject jsonObject = new JSONObject(json);
        assertEquals(true, jsonObject.has("timestamp"));
    }

    @Test
    public void addKeyAndValueToJsonTest() {
        JsonModifier jsonModifier = new JsonModifier();
        String json = jsonModifier.addKeyAndValueToJson("{ name: \"Jamshedi\", age: 23, city: \"Frankfurt am Main\" }", "testkey", "testval");
        JSONObject jsonObject = new JSONObject(json);
        assertEquals(true, jsonObject.has("testkey"));
    }

    @Test
    public void extractJsonArrayValuesTest() {
        PropertyManager propertyManager = new PropertyManager();
        propertyManager.loadPropertyFile("src/test/resources/ELCOMP2.properties");
        JsonModifier jsonModifier = new JsonModifier();
        String json ="{\"took\":3,\"timed_out\":false,\"_shards\":{\"total\":1,\"successful\":1,\"skipped\":0,\"failed\":0},\"hits\":{\"total\":{\"value\":8,\"relation\":\"eq\"},\"max_score\":0.36958414,\"hits\":[{\"_index\":\"ladoop\",\"_type\":\"pdf\",\"_id\":\"HgZjQnIB4LS1nJyEKtZ0\",\"_score\":0.36958414,\"_source\":{\"content\":\"Test Inhalt\",\"filename\":\"Test1.pdf\",\"source\":\"hdfs://localhost:9000/ElData/Test1.pdf\"}},{\"_index\":\"ladoop\",\"_type\":\"pdf\",\"_id\":\"HgZjQnIB4LS1nJyEKtZ0\",\"_score\":0.36958414,\"_source\":{\"content\":\"Test Inhalt\",\"filename\":\"Test2.pdf\",\"source\":\"hdfs://localhost:9000/ElData/Test2.pdf\"}}]}}";
        assertEquals("hdfs://localhost:9000/ElData/Test1.pdf",jsonModifier.extractJsonArrayValues(json).get(0));
}

}
