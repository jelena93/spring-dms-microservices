package descriptor.mapper;

import descriptor.command.DocumentTypeCmd;
import descriptor.domain.DocumentType;
import descriptor.dto.DocumentTypeDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = { DescriptorMapper.class })
public interface DocumentTypeMapper {

    DocumentType mapToEntity(DocumentTypeCmd documentTypeCmd);

    DocumentTypeDto mapToModel(DocumentType documentType);

    List<DocumentTypeDto> mapToModelList(List<DocumentType> documentTypeList);

}
