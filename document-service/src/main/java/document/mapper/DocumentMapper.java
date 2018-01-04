package document.mapper;

import document.command.DocumentCmd;
import document.domain.Document;
import document.dto.DocumentDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DocumentMapper {

    DocumentDto mapToModel(Document document);

    Document mapToEntity(DocumentCmd documentCmd);

    List<DocumentDto> mapToModelList(List<Document> documentList);

}
