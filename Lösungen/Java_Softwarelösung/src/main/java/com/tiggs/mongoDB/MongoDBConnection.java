package com.tiggs.mongoDB;

import com.tiggs.core.IConnection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.tiggs.core.PropertyManager.PropertyMap;

public class MongoDBConnection implements IConnection {
    private static MongoDBConnection instance = new MongoDBConnection();
    private MongoClient mongoClient = null;
    private static final Logger MongoDBLogger = LogManager.getLogger(MongoDBConnection.class);

    public MongoClient connect() throws RuntimeException {
        if (mongoClient == null) {
            MongoDBLogger.info("Connecting to MongoDB at \"" + PropertyMap.get("MongoURI").toString() + "\"");
            MongoClientURI clientURI = new MongoClientURI(PropertyMap.get("MongoURI").toString());
            try {
                mongoClient = new MongoClient(clientURI);
            } catch (MongoException ex) {
                MongoDBLogger.error("An error occoured when connecting to MongoDB", ex.getMessage());
            }
        }
        return mongoClient;
    }

    public void close() {
        MongoDBLogger.info("Closing MongoDB connection");
        if (mongoClient != null) {
            try {
                mongoClient.close();
                mongoClient = null;
            } catch (Exception ex) {
                MongoDBLogger.error(("An error occurred when closing the MongoDB connection: " + ex.getMessage()));
            }
        }
    }

    public static MongoDBConnection getInstance() {
        return instance;
    }
}