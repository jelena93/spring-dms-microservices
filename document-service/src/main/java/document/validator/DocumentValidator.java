package document.validator;

import document.domain.Descriptor;
import document.domain.Document;
import document.dto.DocumentDto;
import document.elasticsearch.ElasticSearchMapper;
import document.elasticsearch.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class DocumentValidator {
    private final MessageSource messageSource;
    private final DocumentService documentService;

    @Autowired
    public DocumentValidator(MessageSource messageSource, DocumentService documentService) {
        this.messageSource = messageSource;
        this.documentService = documentService;
    }

    public void validate(Document document) throws Exception {
        if (document.getFileName() == null)
            throw new Exception(messageSource.getMessage("validation.document.fileName.null", null, Locale.getDefault()));
        if (document.getFileName().isEmpty())
            throw new Exception(messageSource.getMessage("validation.document.fileName.empty", null, Locale.getDefault()));
        if (document.getOwnerId() == null)
            throw new Exception(messageSource.getMessage("validation.document.ownerId.null", null, Locale.getDefault()));

        List<DocumentDto> documentsWithSameName = ElasticSearchMapper.mapToDocumentList(
                documentService.findByName(document.getOwnerId(), document.getFileName()));

        if (!documentsWithSameName.isEmpty())
            throw new Exception("Document with same name already exists");

        validateDescriptors(document);
    }

    public void validateDescriptors(Document document) throws Exception {
        int numberOfIdenticalDescriptors = 0;
        Long existingDocumentID = null;

        for (Descriptor d : document.getDescriptors()) {
            Long id = checkIfDescriptorExists(document.getOwnerId(), d);
            System.out.println("id " + id);
            if (id == null) continue;
            else if (existingDocumentID == null) existingDocumentID = id;
            else if (!Objects.equals(id, existingDocumentID)) continue;
            numberOfIdenticalDescriptors++;
        }
        if (numberOfIdenticalDescriptors == document.getDescriptors().size() && existingDocumentID != null)
            throw new Exception("Document with same descriptors already exists " + existingDocumentID);

    }

    private Long checkIfDescriptorExists(long ownerId, Descriptor newDescriptor) throws IOException {
        List<DocumentDto> documentDtos = ElasticSearchMapper.mapToDocumentList(documentService.findDocumentsForOwnerByDescriptors(newDescriptor));
        if (documentDtos.isEmpty()) return null;

        List<DocumentDto> documentDtosWithSameOwner = documentDtos.stream().filter(documentDto -> documentDto.getOwnerId().equals(ownerId)).collect(Collectors.toList());
        if (documentDtosWithSameOwner.isEmpty()) return null;

        DocumentDto documentDto = documentDtosWithSameOwner.get(0);
        if (documentDto.getId().equals(ownerId)) return documentDto.getId();

        return null;
    }

}