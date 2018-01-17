package company.mapper;

import company.command.UserCmd;
import company.domain.User;
import company.dto.UserDto;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
@DecoratedWith(UserMapperDecorator.class)
public interface UserMapper {

    User mapToEntity(UserCmd userCmd) throws Exception;

    @Mappings({ @Mapping(target = "companyId", source = "user.company.id") })
    UserDto mapToModel(User user);

}
