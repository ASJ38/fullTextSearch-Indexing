package com.tiggs.elastic;

import com.tiggs.core.JsonModifier;
import com.tiggs.core.OutputFile;
import com.tiggs.core.PropertyManager;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.RequestOptions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ElsSearchRequestTest {
    @Test
    public void indexValueTest() throws IOException {
        PropertyManager propertyManager = new PropertyManager();
        JsonModifier jsonModifier = new JsonModifier();
        OutputFile outputFile = new OutputFile();
        ElsSearchRequestCreator elsSearchRequestCreator = new ElsSearchRequestCreator();
        propertyManager.loadPropertyFile("src/main/resources/ELCOMP.properties");
        SearchRequest searchRequest = elsSearchRequestCreator.createSearchRequest();
        String searchResponseAsString = ElsConnection.getInstance().connect().search(searchRequest, RequestOptions.DEFAULT).toString();
        ElsConnection.getInstance().close();
        ArrayList<String> jsonArrayValueList = jsonModifier.extractJsonArrayValues(searchResponseAsString);
        assertTrue(jsonArrayValueList.contains("hdfs://localhost:9000/ElData/ElasticBachelor.pdf"));


//        outputFile.createElsSummaryFile(jsonArrayValueList);
//        File file = new File("ElsSearchSummary.txt");
//        List<String> content = Files.readAllLines(file.toPath(), StandardCharsets.US_ASCII);
//        assertTrue(content.get(content.size() - 1).contains("hdfs://localhost:9000/ElData/ElasticBachelor.pdf"));
    }
}



