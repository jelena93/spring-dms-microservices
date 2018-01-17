package process.mapper;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import process.command.ProcessCmd;
import process.domain.Process;

@Mapper(componentModel = "spring")
@DecoratedWith(ProcessMapperDecorator.class)
public interface ProcessMapper {

    @Mapping(target = "parent", ignore = true)
    Process mapToEntity(ProcessCmd processCmd);

    @Mapping(target = "parent", ignore = true)
    @Mapping(target = "ownerId", ignore = true)
    void updateEntityFromModel(ProcessCmd locationCmd, @MappingTarget Process process);
}
