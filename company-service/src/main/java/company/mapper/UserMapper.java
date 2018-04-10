package company.mapper;

import company.command.UserCmd;
import company.domain.User;
import company.dto.UserDto;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
@DecoratedWith(UserMapperDecorator.class)
public interface UserMapper {

    User mapToEntity(UserCmd userCmd) throws Exception;

    @Mapping(source = "company.name", target = "company")
    UserDto mapToModel(User user);

    List<UserDto> mapToModelList(List<User> userList);


}
