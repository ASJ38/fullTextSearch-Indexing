package com.tiggs.mongoDB;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.tiggs.core.PropertyManager;
import org.bson.Document;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MongoDBConnectionTest {

    @Test
    public void mongoDBInsertionTest() {
        PropertyManager propertyManager = new PropertyManager();
        propertyManager.loadPropertyFile("src/main/resources/ELCOMP.properties");
        String timestamp = new Timestamp(System.currentTimeMillis()).toString();
        String json = "{name: \"Jamshedi\",age: \"23\",city: \"Frankfurt am Main\",timestamp:\"" + timestamp + "\"}";
        Document document = Document.parse(json);
        MongoCollection mongoCollection = MongoDBConnection.getInstance().connect().getDatabase("ELTEST").getCollection("TestCollection1");
        mongoCollection.insertOne(document);
        FindIterable<Document> docs = mongoCollection.find();
        if (docs != null) {
            for (Document doc : docs) {
                if (doc.get("timestamp").toString().equals(timestamp)) {
                    assertTrue(true, "Insertion at: " + timestamp + " failed");
                }
            }
        }
        MongoDBConnection.getInstance().close();

    }
}
