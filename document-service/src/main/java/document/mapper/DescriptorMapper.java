package document.mapper;

import document.domain.Descriptor;
import document.dto.DescriptorDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DescriptorMapper {

    DescriptorDto mapToModel(Descriptor descriptor);

    List<DescriptorDto> mapToModelList(List<Descriptor> descriptorList);

}
