package document.elasticsearch.service;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;

import document.command.DocumentValidationCmd;
import document.domain.Descriptor;
import document.elasticsearch.ElasticClient;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.metrics.max.MaxAggregationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DocumentService {

    private final ElasticClient elasticClient;

    @Autowired
    public DocumentService(ElasticClient elasticClient) {this.elasticClient = elasticClient;}

    public SearchResponse getMaxId() {
        MaxAggregationBuilder aggregation = AggregationBuilders.max("id").field("id");
        return elasticClient.getClient().prepareSearch("documents").setTypes("documents").addAggregation(aggregation)
                            .execute().actionGet();
    }

    public SearchResponse getAllDocuments() {
        return elasticClient.getClient().prepareSearch("documents").setTypes("documents").setQuery(matchAllQuery())
                            .execute().actionGet();
    }

    public SearchResponse findOne(long ownerId, long documentId) {
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        boolQuery.must(QueryBuilders.termQuery("ownerId", ownerId));
        boolQuery.must(QueryBuilders.termQuery("id", documentId));
        return elasticClient.getClient().prepareSearch("documents").setTypes("documents").setQuery(boolQuery).execute()
                            .actionGet();
    }

    public SearchResponse findAll(long ownerId) {
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        boolQuery.must(QueryBuilders.termQuery("ownerId", ownerId));
        return elasticClient.getClient().prepareSearch("documents").setTypes("documents").setQuery(boolQuery).execute()
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
        return elasticClient.getClient().prepareSearch("documents").setTypes("documents").setQuery(boolQuery)
                            .setFrom(offset).setSize(limit).execute().actionGet();
    }

    public SearchResponse findByName(Long ownerId, String fileName) {
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        boolQuery.must(QueryBuilders.termQuery("ownerId", ownerId));
        boolQuery.must(QueryBuilders.matchQuery("fileName", fileName));
        System.out.println(boolQuery);
        return elasticClient.getClient().prepareSearch("documents").setTypes("documents").setQuery(boolQuery).execute()
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
            //            boolQuery.must(QueryBuilders.termQuery("descriptors.documentTypeId", d.getDocumentTypeId()));
            //            boolQuery.must(QueryBuilders.matchQuery("descriptors.descriptorKey", descriptor
            // .getDescriptorKey()));
            //            boolQuery.must(QueryBuilders.matchQuery("descriptors.descriptorValue", descriptor
            // .getDescriptorValue()));
        }
        System.out.println("findDocumentsForOwnerByDescriptors " + boolQuery);
        return elasticClient.getClient().prepareSearch("documents").setTypes("documents").setQuery(boolQuery)
                            .setFrom(offset).setSize(limit).execute().actionGet();
    }
}