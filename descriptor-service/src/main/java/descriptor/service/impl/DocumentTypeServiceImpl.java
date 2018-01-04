package descriptor.service.impl;

import descriptor.domain.DocumentType;
import descriptor.repository.DocumentTypeRepository;
import descriptor.service.DocumentTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentTypeServiceImpl implements DocumentTypeService {
    @Autowired
    DocumentTypeRepository documentTypeRepository;

    @Override
    public DocumentType save(DocumentType documentType) {
        return documentTypeRepository.save(documentType);
    }

    @Override
    public DocumentType findOne(Long id) {
        return documentTypeRepository.findOne(id);
    }

    @Override
    public List<DocumentType> findAll() {
        return documentTypeRepository.findAll();
    }
}
