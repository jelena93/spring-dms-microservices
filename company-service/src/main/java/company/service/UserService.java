package company.service;

import company.domain.User;

import java.util.List;

public interface UserService {

    User save(User user);

    User findOne(String username);

    List<User> findAll();

    List<User> search(String searchTerm);


}
