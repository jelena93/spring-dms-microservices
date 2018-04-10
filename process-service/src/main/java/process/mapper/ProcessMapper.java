package process.mapper;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import process.command.ProcessCmd;
import process.domain.Process;
import process.dto.ProcessDto;

@Mapper(componentModel = "spring")
@DecoratedWith(ProcessMapperDecorator.class)
public interface ProcessMapper {

    @Mapping(target = "parent", ignore = true)
    Process mapToEntity(ProcessCmd processCmd);

    @Mapping(target = "ownerId", ignore = true)
    void updateEntityFromModel(ProcessCmd processCmd, @MappingTarget Process process);

    ProcessDto mapToModel(Process process);
}
