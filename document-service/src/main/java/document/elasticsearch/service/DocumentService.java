package document.elasticsearch.service;

import document.command.DocumentValidationCmd;
import document.domain.Descriptor;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.metrics.max.MaxAggregationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DocumentService {
    private final String elasticsearchIndexName;
    private final String elasticsearchTypeName;
    private final Client elasticSearchClient;

    @Autowired
    public DocumentService(@Value("${elasticsearch.indexName}") String elasticsearchIndexName,
                           @Value("${elasticsearch.typeName}") String elasticsearchTypeName,
                           Client elasticSearchClient) {
        this.elasticsearchIndexName = elasticsearchIndexName;
        this.elasticsearchTypeName = elasticsearchTypeName;
        this.elasticSearchClient = elasticSearchClient;
    }

    public SearchResponse getMaxId() {
        MaxAggregationBuilder aggregation = AggregationBuilders.max("id").field("id");
        return elasticSearchClient.prepareSearch(elasticsearchIndexName).setTypes(elasticsearchTypeName).addAggregation(aggregation)
                .execute().actionGet();
    }

    public SearchResponse findOne(long ownerId, long documentId) {
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        boolQuery.must(QueryBuilders.termQuery("ownerId", ownerId));
        boolQuery.must(QueryBuilders.termQuery("id", documentId));
        return elasticSearchClient.prepareSearch(elasticsearchIndexName).setTypes(elasticsearchTypeName).setQuery(boolQuery).execute()
                .actionGet();
    }

    public SearchResponse findAll(long ownerId) {
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        boolQuery.must(QueryBuilders.termQuery("ownerId", ownerId));
        return elasticSearchClient.prepareSearch(elasticsearchIndexName).setTypes(elasticsearchTypeName).setQuery(boolQuery).execute()
                .actionGet();
    }

    public SearchResponse searchDocumentsForOwner(Long ownerId, String query, int limit, int page) {
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
        return elasticSearchClient.prepareSearch(elasticsearchIndexName).setTypes(elasticsearchTypeName).setQuery(boolQuery)
                .setFrom(offset).setSize(limit).execute().actionGet();
    }

    public SearchResponse findByName(Long ownerId, String fileName) {
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        boolQuery.must(QueryBuilders.termQuery("ownerId", ownerId));
        boolQuery.must(QueryBuilders.matchQuery("fileName", fileName));
        System.out.println(boolQuery);
        return elasticSearchClient.prepareSearch(elasticsearchIndexName).setTypes(elasticsearchTypeName).setQuery(boolQuery).execute()
                .actionGet();
    }

    public SearchResponse findDocumentsForOwnerByDescriptors(DocumentValidationCmd d, int limit, int page) {
        int offset = (page - 1) * limit;
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        boolQuery.must(QueryBuilders.termQuery("ownerId", d.getOwnerId()));
        MatchQueryBuilder matchQuery = QueryBuilders.matchQuery("descriptors.documentTypeId", d.getDocumentTypeId());
        boolQuery.must(matchQuery);
        for (Descriptor descriptor : d.getDescriptors()) {
            matchQuery = QueryBuilders.matchQuery("descriptors.descriptorKey", descriptor.getDescriptorKey());
            boolQuery.must(matchQuery);
            matchQuery = QueryBuilders.matchQuery("descriptors.descriptorValue", descriptor.getDescriptorValue());
            boolQuery.must(matchQuery);
        }
        System.out.println("findDocumentsForOwnerByDescriptors " + boolQuery);
        return elasticSearchClient.prepareSearch(elasticsearchIndexName).setTypes(elasticsearchTypeName).setQuery(boolQuery)
                .setFrom(offset).setSize(limit).execute().actionGet();
    }
}