package auth.mapper;

import auth.command.UserCmd;
import auth.domain.Authority;
import auth.domain.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public abstract class UserMapperDecorator implements UserMapper {
    @Autowired
    private UserMapper delegate;

    @Override
    public User mapToEntity(UserCmd userCmd) {
        User user = delegate.mapToEntity(userCmd);
        List<Authority> authorityList = userCmd.getRoles().stream()
                                               .map(role -> new Authority("ROLE_" + role.toUpperCase())).
                                                       collect(Collectors.toList());
        user.setAuthorities(authorityList);
        return user;
    }
}
