package auth.service.impl;

import auth.domain.User;
import auth.repository.UserRepository;
import auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User save(User user) {
        System.out.println("Saving " + user);
        return userRepository.save(user);
    }
}
