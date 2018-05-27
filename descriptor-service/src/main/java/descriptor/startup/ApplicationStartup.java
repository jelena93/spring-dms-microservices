package descriptor.startup;

import descriptor.domain.Descriptor;
import descriptor.domain.DescriptorType;
import descriptor.domain.DocumentType;
import descriptor.service.DescriptorTypeService;
import descriptor.service.DocumentTypeService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ApplicationStartup implements InitializingBean {

    @Value("${addToDb}")
    private boolean addToDb;
    private final DocumentTypeService documentTypeService;
    private final DescriptorTypeService descriptorTypeService;

    @Autowired
    public ApplicationStartup(DocumentTypeService documentTypeService, DescriptorTypeService descriptorTypeService) {
        this.documentTypeService = documentTypeService;
        this.descriptorTypeService = descriptorTypeService;
    }

    @Override
    public void afterPropertiesSet() {
        if (addToDb) {
            DescriptorType descriptorTypeInteger = new DescriptorType(Integer.class);
            DescriptorType descriptorTypeDouble = new DescriptorType(Double.class);
            DescriptorType descriptorTypeDate = new DescriptorType(Date.class);

            descriptorTypeInteger = descriptorTypeService.save(descriptorTypeInteger);
            descriptorTypeDouble = descriptorTypeService.save(descriptorTypeDouble);
            descriptorTypeDate = descriptorTypeService.save(descriptorTypeDate);

            DocumentType documentType = new DocumentType("Nalog za placanje");
            Descriptor descriptor = new Descriptor("Broj naloga", documentType, descriptorTypeInteger);
            documentType.getDescriptors().add(descriptor);
            descriptor = new Descriptor("Suma", documentType, descriptorTypeDouble);
            documentType.getDescriptors().add(descriptor);
            descriptor = new Descriptor("Datum", documentType, descriptorTypeDate);
            documentType.getDescriptors().add(descriptor);
            documentTypeService.save(documentType);

            documentType = new DocumentType("Profaktura dobavljaca");
            documentType = documentTypeService.save(documentType);
            descriptor = new Descriptor("Broj profakture", documentType, descriptorTypeInteger);
            documentType.getDescriptors().add(descriptor);
            descriptor = new Descriptor("Datum", documentType, descriptorTypeDate);
            documentType.getDescriptors().add(descriptor);
            descriptor = new Descriptor("Suma", documentType, descriptorTypeDouble);
            documentType.getDescriptors().add(descriptor);
            documentTypeService.save(documentType);
        }

    }
}
