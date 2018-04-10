package company.service.impl;

import company.domain.User;
import company.repository.UserRepository;
import company.repository.UserSpecs;
import company.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findOne(String username) {
        return userRepository.findOne(username);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    public List<User> search(String searchTerm) {
        return userRepository.findAll(UserSpecs.searchUsers(searchTerm));
    }
}
