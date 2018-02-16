package auth.messaging;

import auth.command.UserCmd;
import auth.mapper.UserMapper;
import auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;

@Service
public class UserMessagingHandler {
    private final UserService userService;
    private final UserMapper userMapper;

    @Autowired
    public UserMessagingHandler(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @StreamListener(target = UserInputChannel.USER_ADDED_INPUT)
    public void handleUserAdded(UserCmd user) {
        System.out.println("handleUserAdded " + user);
        userService.save(userMapper.mapToEntity(user));
    }
}
