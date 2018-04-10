package auth.service;

import auth.domain.User;
import auth.dto.UserDetails;
import auth.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component("userDetailsService")
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(UserDetailsService.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(final String login) {

        log.debug("Authenticating {}", login);
        String lowercaseLogin = login.toLowerCase();

        User user = userRepository.findByUsernameCaseInsensitive(lowercaseLogin);

        if (user == null) {
            throw new UsernameNotFoundException("User " + lowercaseLogin + " was not found in the database");
        }

        List<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
                .map(a -> new SimpleGrantedAuthority(
                        a.getName()))
                .collect(Collectors.toList());

        return new UserDetails(user.getUsername(), user.getPassword(),
                user.getCompanyId(), true, grantedAuthorities);

    }

}
