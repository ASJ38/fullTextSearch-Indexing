package com.tiggs.elastic;
import com.tiggs.core.PropertyManager;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ElsConnectionTest {

    @Test
    public void connectionTest() throws IOException {
        PropertyManager propertyManager = new PropertyManager();
        propertyManager.loadPropertyFile("src/test/resources/ELCOMP.properties");
        RestHighLevelClient client = ElsConnection.getInstance().connect();
        boolean searchResponse= client.ping(RequestOptions.DEFAULT);
        assertEquals(true,searchResponse);
       ElsConnection.getInstance().close();
    }
}
