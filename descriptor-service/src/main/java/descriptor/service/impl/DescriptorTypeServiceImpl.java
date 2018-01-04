package descriptor.service.impl;

import descriptor.domain.DescriptorType;
import descriptor.repository.DescriptorTypeRepository;
import descriptor.service.DescriptorTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DescriptorTypeServiceImpl implements DescriptorTypeService {
    @Autowired
    DescriptorTypeRepository descriptorTypeRepository;

    @Override
    public DescriptorType save(DescriptorType descriptorType) {
        return descriptorTypeRepository.save(descriptorType);
    }
}
