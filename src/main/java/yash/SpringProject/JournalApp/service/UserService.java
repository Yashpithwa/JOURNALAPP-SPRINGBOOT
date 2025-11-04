package yash.SpringProject.JournalApp.service;

import org.bson.types.ObjectId;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import yash.SpringProject.JournalApp.Entity.User;
import yash.SpringProject.JournalApp.repositiory.UserRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User saveEntry(User user) {
        if (user.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            user.setRoles(Arrays.asList("USER"));
        }
        return userRepository.save(user);
    }
    public User saveAdmin(User user) {
        if (user.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            user.setRoles(Arrays.asList("USER","ADMIN"));
        }
        return userRepository.save(user);
    }
    public void saveUser(User user) {
        userRepository.save(user);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(ObjectId id) {
        return userRepository.findById(id);
    }

    public void deleteById(ObjectId id) {
        userRepository.deleteById(id);
    }

    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }
}
