package com.tiggs.core;

import java.io.*;
import java.sql.Timestamp;
import java.util.ArrayList;

import static com.tiggs.core.PropertyManager.PropertyMap;
import static com.tiggs.core.PropertyManager.CoreLogger;

public class OutputFile {

    public void createElsSummaryFile(ArrayList<String> valueList) throws IOException {
        Writer writer = null;
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        try {
            CoreLogger.info("Writing summary file");
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream("ElsSearchSummary.txt"), "utf-8"));
            writer.write("Search term: " + PropertyMap.get("ElsFieldvalue").toString() + "\n");
            writer.write("Elasticsearch index: " + PropertyMap.get("ElsIndex").toString() + "\n");
            writer.write("MongoDB database: " + PropertyMap.get("MongoDataBaseName").toString() + "\n");
            writer.write("MongoDB collection: " + PropertyMap.get("MongoCollectionName").toString() + "\n");
            writer.write("Timestamp: " + timestamp.toString() + "\n");
            writer.write("Search term occurrences: " + valueList.size() + "\n");
            for (int i = 0; i < valueList.size(); i++) {
                writer.write((i + 1) + "." + valueList.get(i) + "\n");
            }
            CoreLogger.info("Created summary file with \"" + valueList.size() +"\" hits");
        } catch (IOException ex) {
            CoreLogger.error("Creating summary file failed" + ex.getMessage());
        } finally {
            writer.close();
        }
    }
}
