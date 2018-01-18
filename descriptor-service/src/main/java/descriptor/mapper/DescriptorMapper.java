package descriptor.mapper;

import descriptor.domain.Descriptor;
import descriptor.dto.DescriptorDto;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
@DecoratedWith(DescriptorMapperDecorator.class)
public interface DescriptorMapper {

    Descriptor mapToEntity(DescriptorDto descriptorDto);

    @Mappings({ @Mapping(target = "paramClass", source = "descriptor.descriptorType.paramClass") })
    DescriptorDto mapToModel(Descriptor descriptor);

    List<DescriptorDto> mapToModelList(List<Descriptor> descriptorList);

}
