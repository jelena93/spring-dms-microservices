package descriptor.mapper;

import descriptor.domain.DocumentType;
import descriptor.dto.DocumentTypeDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = { DescriptorMapper.class })
public interface DocumentTypeMapper {

    DocumentType mapToEntity(DocumentTypeDto documentTypeDto);

    DocumentTypeDto mapToModel(DocumentType documentType);

    List<DocumentTypeDto> mapToModelList(List<DocumentType> documentTypeList);

}
