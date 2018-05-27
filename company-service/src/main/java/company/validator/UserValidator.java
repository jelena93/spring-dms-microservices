package company.validator;

import company.command.UserCmd;
import company.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserValidator {
    private final UserService userService;

    @Autowired
    public UserValidator(UserService userService) {
        this.userService = userService;
    }

    public void validate(UserCmd userCmd) throws Exception {
        if (!userService.findByUsername(userCmd.getUsername()).isEmpty())
            throw new Exception("User with username " + userCmd.getUsername() + " already exists");

    }
}
