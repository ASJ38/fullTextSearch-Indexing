package com.tiggs.core;

import com.tiggs.elastic.ElsConnection;
import com.tiggs.elastic.ElsSearchRequestCreator;
import com.tiggs.mongoDB.MongoDBConnection;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.RequestOptions;

import static com.tiggs.core.PropertyManager.PropertyMap;
import static com.tiggs.core.PropertyManager.CoreLogger;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        PropertyManager propertyManager = new PropertyManager();
        JsonModifier jsonModifier = new JsonModifier();
        OutputFile outputFile = new OutputFile();
        ElsSearchRequestCreator elsSearchRequestCreator = new ElsSearchRequestCreator();
        propertyManager.loadPropertyFile(args[0]);
        CoreLogger.info("Executing search request");
        SearchRequest searchRequest = elsSearchRequestCreator.createSearchRequest();
        String searchResponseAsString = ElsConnection.getInstance().connect().search(searchRequest, RequestOptions.DEFAULT).toString();
        ElsConnection.getInstance().close();

        ArrayList<String> jsonArrayValueList = jsonModifier.extractJsonArrayValues(searchResponseAsString);
        outputFile.createElsSummaryFile(jsonArrayValueList);
        searchResponseAsString = jsonModifier.addKeyAndValueToJson(searchResponseAsString, "search_term", PropertyMap.get("ElsFieldvalue").toString());
        searchResponseAsString = jsonModifier.addKeyAndValueToJson(searchResponseAsString, "searched_by", PropertyMap.get("ElsUser").toString());
        searchResponseAsString = jsonModifier.addTimestampToJson(searchResponseAsString);

        MongoCollection mongoCollection = MongoDBConnection.getInstance().connect().getDatabase(PropertyMap.get("MongoDataBaseName").toString()).getCollection(PropertyMap.get("MongoCollectionName").toString());
        Document document = Document.parse(searchResponseAsString);
        CoreLogger.info("Inserting search response to MongoDB");
        mongoCollection.insertOne(document);
        MongoDBConnection.getInstance().close();
    }
}
