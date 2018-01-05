package process.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import process.command.ActivityCmd;
import process.domain.Activity;

@Mapper(componentModel = "spring")
public interface ActivityMapper {

    Activity mapToEntity(ActivityCmd activityCmd);

    void updateEntityFromModel(ActivityCmd activityCmd, @MappingTarget Activity activity);

}
