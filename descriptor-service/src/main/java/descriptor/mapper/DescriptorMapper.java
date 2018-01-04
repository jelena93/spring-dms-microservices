package descriptor.mapper;

import descriptor.domain.Descriptor;
import descriptor.dto.DescriptorDto;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
@DecoratedWith(DescriptorMapperDecorator.class)
public interface DescriptorMapper {

    Descriptor mapToEntity(DescriptorDto descriptorDto);

    DescriptorDto mapToModel(Descriptor descriptor);

    List<DescriptorDto> mapToModelList(List<Descriptor> descriptorList);

}
