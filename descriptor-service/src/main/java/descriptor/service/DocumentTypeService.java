package descriptor.service;

import descriptor.domain.DocumentType;

import java.util.List;

public interface DocumentTypeService {

    DocumentType save(DocumentType documentType);

    DocumentType findOne(Long id);

    List<DocumentType> findAll();

}
