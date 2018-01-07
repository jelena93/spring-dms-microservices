package company.mapper;

import company.command.UserCmd;
import company.domain.User;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
@DecoratedWith(UserMapperDecorator.class)
public interface UserMapper {

    User mapToEntity(UserCmd userCmd) throws Exception;

}
