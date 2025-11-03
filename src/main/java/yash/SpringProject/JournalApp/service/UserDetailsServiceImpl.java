package yash.SpringProject.JournalApp.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import yash.SpringProject.JournalApp.Entity.User;
import yash.SpringProject.JournalApp.repositiory.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with userName " + username);
        }

        // ensure roles is not null
        List<String> roles = user.getRoles();
        if (roles == null || roles.isEmpty()) {
            roles = List.of("USER"); // fallback role
        }

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUserName())
                .password(user.getPassword())
                .roles(roles.toArray(new String[0]))
                .build();
    }
}
