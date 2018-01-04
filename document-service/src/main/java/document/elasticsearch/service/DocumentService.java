package document.elasticsearch.service;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;

import document.elasticsearch.ElasticClient;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.metrics.max.MaxAggregationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class DocumentService {

    @Autowired
    private ElasticClient elasticClient;

    public SearchResponse getMaxId() throws IOException {
        MaxAggregationBuilder aggregation = AggregationBuilders.max("id").field("id");
        return elasticClient.getClient().prepareSearch("documents").setTypes("documents").addAggregation(aggregation)
                            .execute().actionGet();
    }

    public SearchResponse getAllDocuments() throws IOException {
        return elasticClient.getClient().prepareSearch("documents").setTypes("documents").setQuery(matchAllQuery())
                            .execute().actionGet();
    }

    public SearchResponse searchDocumentsForOwner(Long ownerId, String query, int limit, int page) throws IOException {
        int offset = (page - 1) * limit;
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        boolQuery.must(QueryBuilders.termQuery("ownerId", ownerId));
        if (query != null && !query.isEmpty()) {
            boolQuery.should(QueryBuilders.queryStringQuery('*' + query + '*').field("fileName"))
                     .should(QueryBuilders.queryStringQuery('*' + query + '*').field("attachment.content"))
                     .should(QueryBuilders.queryStringQuery('*' + query + '*').field("descriptors.descriptorKey"))
                     .should(QueryBuilders.queryStringQuery('*' + query + '*').field("descriptors.descriptorValue"))
                     .minimumShouldMatch(1);
        }
        System.out.println(boolQuery);
        return elasticClient.getClient().prepareSearch("documents").setTypes("documents").setQuery(boolQuery)
                            .setFrom(offset).setSize(limit).execute()

                            .actionGet();
    }

}