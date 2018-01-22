package document.mapper;

import document.command.DocumentCmd;
import document.domain.Document;
import document.elasticsearch.service.DocumentService;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.aggregations.metrics.max.Max;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

public abstract class DocumentMapperDecorator implements DocumentMapper {
    @Autowired
    private DocumentMapper delegate;
    @Autowired
    private DocumentService documentService;

    @Override
    public Document mapToEntity(DocumentCmd documentCmd) throws IOException {
        Document document = delegate.mapToEntity(documentCmd);

        try {
            SearchResponse sr = documentService.getMaxId();
            Max max = sr.getAggregations().get("id");
            System.out.println("max: " + max.getValue());
            if (max.getValue() < 0) {
                document.setId(1L);
            } else {
                document.setId((long) max.getValue() + 1);
            }
        } catch (Exception e) {
            document.setId(1L);
            System.out.println("no index - " + e.getMessage());
        }
        return document;
    }
}
