package document.elasticsearch;

import com.fasterxml.jackson.databind.ObjectMapper;
import document.domain.Descriptor;
import document.domain.Document;
import document.dto.DocumentDto;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

@Service
public class DocumentIndexer {

    private final String elasticsearchPipelineName;
    private final String elasticsearchIndexName;
    private final String elasticsearchTypeName;
    private final String elasticsearchNumberOfShards;
    private final String elasticsearchNumberOfReplicas;
    private final Client elasticSearchClient;

    @Autowired
    public DocumentIndexer(Client elasticSearchClient,
                           @Value("${elasticsearch.pipelineName}") String elasticsearchPipelineName,
                           @Value("${elasticsearch.indexName}") String elasticsearchIndexName,
                           @Value("${elasticsearch.typeName}") String elasticsearchTypeName,
                           @Value("${elasticsearch.numberOfShards}") String elasticsearchNumberOfShards,
                           @Value("${elasticsearch.numberOfReplicas}") String elasticsearchNumberOfReplicas) {
        this.elasticSearchClient = elasticSearchClient;
        this.elasticsearchPipelineName = elasticsearchPipelineName;
        this.elasticsearchIndexName = elasticsearchIndexName;
        this.elasticsearchTypeName = elasticsearchTypeName;
        this.elasticsearchNumberOfShards = elasticsearchNumberOfShards;
        this.elasticsearchNumberOfReplicas = elasticsearchNumberOfReplicas;
    }

    public void createIndex() throws Exception {
        if (!checkIfIndexExists()) {
            System.out.println("creating index " + elasticsearchIndexName);
            elasticSearchClient.admin().indices().prepareCreate(elasticsearchIndexName)
                    .setSettings(createSettings())
                    .addMapping(elasticsearchTypeName, createMappings())
                    .execute()
                    .actionGet();
        }
    }

//
//    public void testQuery() throws Exception {
//        SearchResponse searchResponse = elasticSearchClient.prepareSearch(elasticsearchIndexName)
//                .setQuery(
//                        QueryBuilders.queryStringQuery("premn")
//                                .analyzer("autocomplete")
//                )
//                .execute()
//                .actionGet();
//        System.out.println(searchResponse.getHits().getHits().length);
//        List<DocumentDto> dtos = mapToDocumentList(searchResponse);
//        for (DocumentDto d : dtos) {
//            System.out.println("evo " + d.getFileName());
//        }
//    }


    public void save(Document document) throws Exception {
        elasticSearchClient.prepareIndex(elasticsearchIndexName, elasticsearchTypeName, String.valueOf(document.getId()))
                .setPipeline(elasticsearchPipelineName).setSource(buildDocument(document)).get();
    }

    private boolean checkIfIndexExists() {
        return elasticSearchClient.admin().indices().prepareExists(elasticsearchIndexName).execute().actionGet()
                .isExists();
    }

    public void delete(List<Long> documentIds) {
        documentIds.forEach(documentId -> elasticSearchClient.prepareDelete(elasticsearchIndexName, elasticsearchTypeName,
                String.valueOf(documentId)).get());
        System.out.println("deleting documents " + documentIds);
    }

    private XContentBuilder createSettings() throws IOException {
        return
                XContentFactory.jsonBuilder()
                        .startObject()

                        .field("number_of_shards", elasticsearchNumberOfShards)
                        .field("number_of_replicas", elasticsearchNumberOfReplicas)

                        .startObject("analysis")
                        .startObject("analyzer")
                        .startObject("autocomplete")
                        .field("type", "custom")
                        .field("tokenizer", "standard")
                        .field("filter", new String[]{"lowercase", "autocomplete_filter"})
                        .endObject()
                        .endObject()
                        .startObject("filter")
                        .startObject("autocomplete_filter")
                        .field("type", "edge_ngram")
                        .field("min_gram", "1")
                        .field("max_gram", "20")
                        .endObject()
                        .endObject()
                        .endObject()

                        .endObject();
    }

    private static XContentBuilder createMappings() throws IOException {
        return
                XContentFactory.jsonBuilder()
                        .startObject()
                        .startObject("properties")

                        .startObject("id")
                        .field("type", "long")
                        .endObject()

                        .startObject("ownerId")
                        .field("type", "long")
                        .endObject()

                        .startObject("fileName")
                        .field("type", "text")
                        .field("index", true)
                        .field("analyzer", "autocomplete")
                        .field("search_analyzer", "standard")
                        .endObject()

                        .startObject("content")
                        .field("type", "text")
                        .field("index", true)
                        .field("analyzer", "autocomplete")
                        .field("search_analyzer", "standard")
                        .endObject()

                        .startObject("file")
                        .field("type", "binary")
                        .endObject()

                        .startObject("descriptors")
                        .field("type", "nested")

                        .startObject("properties")

                        .startObject("documentTypeId")
                        .field("type", "long")
                        .endObject()

                        .startObject("descriptorKey")
                        .field("type", "text")
                        .field("index", true)
                        .field("analyzer", "autocomplete")
                        .field("search_analyzer", "standard")
                        .endObject()

                        .startObject("descriptorValue")
                        .field("type", "text")
                        .field("index", true)
                        .field("analyzer", "autocomplete")
                        .field("search_analyzer", "standard")
                        .endObject()

                        .endObject()
                        .endObject() //descriptors

                        .endObject() //properties
                        .endObject();
    }

    private static XContentBuilder buildDocument(Document document) throws Exception {
        XContentBuilder builder = jsonBuilder();
        builder.startObject();
        builder.field("id", document.getId());
        builder.field("ownerId", document.getOwnerId());
        builder.field("fileName", document.getFileName());
        List<Descriptor> descriptors = document.getDescriptors();
        builder.startArray("descriptors");
        if (document.getDescriptors() != null) {
            for (Descriptor d : descriptors) {
                builder.startObject();
                builder.field("documentTypeId", d.getDocumentTypeId());
                builder.field("descriptorKeydescriptorKey", d.getDescriptorKey());
                builder.field("descriptorValue", d.getDescriptorValue());
                builder.endObject();
            }
        }
        builder.endArray();
        builder.field("content", document.getContent());
        builder.field("file", document.getFile());
        builder.endObject();
        return builder;
    }

    private static List<DocumentDto> mapToDocumentList(SearchResponse searchResponse) throws IOException {
        List<DocumentDto> documents = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        for (SearchHit hit : searchResponse.getHits()) {
            documents.add(mapper.readValue(hit.getSourceAsString(), DocumentDto.class));
        }
        System.out.println(searchResponse);
        System.out.println("total hits: " + searchResponse.getHits().getTotalHits());
        return documents;
    }


    //    private void insertOrUpdateDocument(long companyID, Document document) throws Exception {
//        IndexRequest indexRequest = new IndexRequest(elasticsearchIndexName, elasticsearchTypeName, String.valueOf(document.getId()))
//                .source(buildDocument(document));
//        UpdateRequest updateRequest = new UpdateRequest(elasticsearchIndexName, elasticsearchTypeName, String.valueOf(document.getId()))
//                .doc(buildDocument(document)).upsert(indexRequest);
//        elasticSearchClient.update(updateRequest).get();
//    }
}
