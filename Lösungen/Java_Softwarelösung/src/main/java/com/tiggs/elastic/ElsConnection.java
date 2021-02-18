package com.tiggs.elastic;

import com.tiggs.core.IConnection;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.ElasticsearchStatusException;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;

import static com.tiggs.core.PropertyManager.PropertyMap;


public class ElsConnection implements IConnection {
    private static ElsConnection instance = new ElsConnection();
    private RestHighLevelClient restHighLevelClient = null;
    private static final Logger ElsLogger = LogManager.getLogger(ElsConnection.class);

    public RestHighLevelClient connect() throws ElasticsearchStatusException {
        if (restHighLevelClient == null) {
            try {
                restHighLevelClient = new RestHighLevelClient(this.getRestClientBuilder());
            } catch (ElasticsearchException ex) {
                ElsLogger.error("An error occoured when connecting to Elasticsearch ", ex.getMessage());
            }
        }
        return restHighLevelClient;
    }

    public void close() {
        if (restHighLevelClient != null) {
            try {
                ElsLogger.info("Closing Elasticsearch connection");
                restHighLevelClient.close();
                restHighLevelClient = null;
            } catch (Exception ex) {
                ElsLogger.error(("An error occurred when closing the Elasticsearch connection: " + ex.getMessage()));
            }
        }
    }

    private RestClientBuilder getRestClientBuilder() {
        int elsHostPort = Integer.parseInt(PropertyMap.get("ElsHostPort").toString());
        //https://www.elastic.co/guide/en/elasticsearch/client/java-rest/current/_basic_authentication.html
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials(PropertyMap.get("ElsUser").toString(), PropertyMap.get("ElsPassword").toString()));
        ElsLogger.info("Connecting to Elasticsearch at \"" + PropertyMap.get("ElsHost") + ": " + elsHostPort + "\" as \"" + PropertyMap.get("ElsUser").toString() + "\"");
        RestClientBuilder builder = RestClient.builder(new HttpHost(PropertyMap.get("ElsHost").toString(), elsHostPort))
                .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                    @Override
                    public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                        return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                    }
                });
        return builder;
    }

    public static ElsConnection getInstance() {
        return instance;
    }
}
