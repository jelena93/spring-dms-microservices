package descriptor.service.impl;

import descriptor.domain.Descriptor;
import descriptor.repository.DescriptorRepository;
import descriptor.service.DescriptorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DescriptorServiceImpl implements DescriptorService {
    private final DescriptorRepository descriptorRepository;

    @Autowired
    public DescriptorServiceImpl(DescriptorRepository descriptorRepository) {
        this.descriptorRepository = descriptorRepository;
    }

    @Override
    public List<Descriptor> save(List<Descriptor> descriptorList) {
        System.out.println("saving descriptors " + descriptorList.size() + '\n' + descriptorList);
        return descriptorRepository.save(descriptorList);
    }

    @Override
    public void removeByDocumentIdIn(List<Long> documentIds) {
        descriptorRepository.deleteByDocumentIdIn(documentIds);
        System.out.println("removing descriptors with doc ids " + documentIds);
    }

    @Override
    public List<Descriptor> findDefaultDescriptorsForDocumentType(long documentTypeId) {
        return descriptorRepository.findByDocumentTypeIdAndDocumentIdIsNull(documentTypeId);
    }
}
