package auth.service;

import auth.domain.User;
import auth.dto.UserDto;
import auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findOne(username);
        if (user == null) {
            throw new UsernameNotFoundException("UserName " + username + " not found");
        }
        UserDto securityUser = new UserDto(user.getUsername(), user.getPassword(), user.getName(),
                                           user.getSurname(), user.getRoles(), user.getRoles().get(0));
        return securityUser;
    }
}
