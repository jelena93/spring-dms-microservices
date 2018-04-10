package document.elasticsearch;

import com.fasterxml.jackson.databind.ObjectMapper;
import document.dto.DocumentDto;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ElasticSearchMapper {

    public static List<DocumentDto> mapToDocumentList(SearchResponse searchResponse) throws IOException {
        List<DocumentDto> documents = new ArrayList<>();
        System.out.println(searchResponse);
        ObjectMapper mapper = new ObjectMapper();
        for (SearchHit hit : searchResponse.getHits()) {
            documents.add(mapper.readValue(hit.getSourceAsString(), DocumentDto.class));
        }
        System.out.println("total hits: " + searchResponse.getHits().getTotalHits());
        return documents;
    }
}
