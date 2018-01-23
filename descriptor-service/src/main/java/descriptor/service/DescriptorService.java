package descriptor.service;

import descriptor.domain.Descriptor;

import java.util.List;

public interface DescriptorService {

    List<Descriptor> save(List<Descriptor> descriptorList);

    void removeByDocumentIdIn(List<Long> documentIds);

}
