package process.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import process.command.ActivityCmd;
import process.domain.Activity;

@Mapper(componentModel = "spring")
public interface ActivityMapper {

    Activity mapToEntity(ActivityCmd activityCmd);

    @Mapping(target = "inputListDocumentTypes", ignore = true)
    @Mapping(target = "outputListDocumentTypes", ignore = true)
    void updateEntityFromModel(ActivityCmd activityCmd, @MappingTarget Activity activity);

}
