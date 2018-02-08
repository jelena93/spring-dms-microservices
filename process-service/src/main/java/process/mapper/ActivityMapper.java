package process.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import process.command.ActivityCmd;
import process.domain.Activity;
import process.dto.ActivityDto;

@Mapper(componentModel = "spring")
public interface ActivityMapper {

    Activity mapToEntity(ActivityCmd activityCmd);

    @Mapping(target = "inputListDocumentTypes", ignore = true)
    @Mapping(target = "outputListDocumentTypes", ignore = true)
    void updateEntityFromModel(ActivityCmd activityCmd, @MappingTarget Activity activity);

    ActivityDto mapToModel(Activity activity);

}
