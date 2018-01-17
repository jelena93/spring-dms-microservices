package company.service.impl;

import company.domain.User;
import company.messaging.UserMessagingService;
import company.repository.UserRepository;
import company.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMessagingService userMessagingService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMessagingService userMessagingService) {
        this.userRepository = userRepository;
        this.userMessagingService = userMessagingService;
    }

    @Override
    public User save(User user) {
        user = userRepository.save(user);
        userMessagingService.sendUserAdded(user);
        return user;
    }
}
