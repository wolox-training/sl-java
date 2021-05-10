package wolox.training.security;

import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import wolox.training.exceptions.NotFoundException;
import wolox.training.models.User;
import wolox.training.repositories.UserRepository;
import wolox.training.utils.MessageError;

@Component
public class CustomAuthenticationProvider implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findFirstByUsername(username).
                orElseThrow(() -> new NotFoundException(MessageError.USER_NOT_FOUND_MSG));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                Collections.emptyList());
    }
}
