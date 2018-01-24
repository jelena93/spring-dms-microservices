package document.elasticsearch;

import static org.elasticsearch.client.Requests.createIndexRequest;

import document.domain.Descriptor;
import document.domain.Document;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class DocumentIndexer {

    @Autowired
    private ElasticClient elasticClient;

    public void indexDocument(Document document) throws Exception {
        elasticClient.getClient().prepareIndex("documents", "documents", String.valueOf(document.getId()))
                     .setPipeline("attachments").setSource(buildDocument(document)).get();
    }

    public void insertOrUpdateDocuments(long companyID, List<Document> documents) throws Exception {
        for (Document document : documents) {
            insertOrUpdateDocument(companyID, document);
        }
    }

    private void insertOrUpdateDocument(long companyID, Document document) throws Exception {
        IndexRequest indexRequest = new IndexRequest("documents", "documents", String.valueOf(document.getId()))
                .source(buildDocument(document));
        UpdateRequest updateRequest = new UpdateRequest("documents", "documents", String.valueOf(document.getId()))
                .doc(buildDocument(document)).upsert(indexRequest);
        elasticClient.getClient().update(updateRequest).get();
    }

    public void createDocumentIndexIfNotExists() {
        boolean exists = elasticClient.getClient().admin().indices().prepareExists("documents").execute().actionGet()
                                      .isExists();
        if (!exists) {
            elasticClient.getClient().admin().indices().
                    create(createIndexRequest("documents")).actionGet();
        }
    }

    public void deleteDocuments(List<Long> documentIds) {
        documentIds.forEach(documentId -> elasticClient.getClient().prepareDelete("documents", "documents",
                                                                                  String.valueOf(documentId)).get());
        System.out.println("deleting documents " + documentIds);
    }

    private XContentBuilder buildDocument(Document document) throws Exception {
        XContentBuilder builder = XContentFactory.jsonBuilder();
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
                builder.field("descriptorKey", d.getDescriptorKey());
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
}
