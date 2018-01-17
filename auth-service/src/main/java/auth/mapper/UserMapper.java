package auth.mapper;

import auth.command.UserCmd;
import auth.domain.User;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
@DecoratedWith(UserMapperDecorator.class)
public interface UserMapper {
    User mapToEntity(UserCmd userCmd);
}
