package yash.SpringProject.JournalApp.service;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import yash.SpringProject.JournalApp.Entity.JournalEntry;
import yash.SpringProject.JournalApp.Entity.User;
import yash.SpringProject.JournalApp.repositiory.JournalEntryRepositiory;
import yash.SpringProject.JournalApp.repositiory.UserRepository;

import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void saveEntry(User user) {
        userRepository.save(user);
    }

    public List<User> getAll(){
        return userRepository.findAll();
    }

    public Optional<User> findById(ObjectId id)
    {
        return userRepository.findById(id);
    }
    public void deleteById(ObjectId id)
    {
       userRepository.deleteById(id);
    }

    public User findByUserName(String userName){
        return userRepository.findByUserName(userName);
    }
}
