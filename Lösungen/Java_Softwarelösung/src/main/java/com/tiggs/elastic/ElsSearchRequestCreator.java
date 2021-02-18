package com.tiggs.elastic;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import static com.tiggs.core.PropertyManager.PropertyMap;

public class ElsSearchRequestCreator {

    public SearchRequest createSearchRequest() {
        SearchRequest searchRequest = new SearchRequest().indices(PropertyMap.get("ElsIndex").toString());
        SearchSourceBuilder searchSourceBuilder = this.createSearchSourceBuilder();
        MatchQueryBuilder matchQueryBuilder = this.createMatchQueryBuilder();
        searchSourceBuilder.query(matchQueryBuilder);
        searchRequest.source(searchSourceBuilder);
        return searchRequest;
    }

    private SearchSourceBuilder createSearchSourceBuilder() {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.from(Integer.parseInt(PropertyMap.get("ElsSearchStartIndex").toString()));
        searchSourceBuilder.size(Integer.parseInt(PropertyMap.get("ElsSearchMaxResponseSize").toString()));
        return searchSourceBuilder;
    }

    private MatchQueryBuilder createMatchQueryBuilder() {
        MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder(PropertyMap.get("ElsFiledname").toString(), PropertyMap.get("ElsFieldvalue").toString());
        return matchQueryBuilder;
    }


}
