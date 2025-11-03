package yash.SpringProject.JournalApp.service;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yash.SpringProject.JournalApp.Entity.JournalEntry;
import yash.SpringProject.JournalApp.Entity.User;
import yash.SpringProject.JournalApp.repositiory.JournalEntryRepositiory;
import yash.SpringProject.JournalApp.repositiory.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class journalEntryService {

    @Autowired
    private JournalEntryRepositiory journalEntryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    public JournalEntry saveEntry(JournalEntry journalEntry, String userName) {
        User user = userService.findByUserName(userName);
        if (user == null) {
            throw new RuntimeException("User not found: " + userName);
        }

        journalEntry.setDate(LocalDateTime.now());
        JournalEntry saved = journalEntryRepository.save(journalEntry);

        if (!user.getJournalEntries().contains(saved)) {
            user.getJournalEntries().add(saved);
            userService.saveUser(user);
        }

        return saved;
    }

    public List<JournalEntry> getAll() {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findById(ObjectId id) {
        return journalEntryRepository.findById(id);
    }

    public void deleteById(ObjectId id, String userName) {
        User user = userService.findByUserName(userName);
        if (user == null) throw new RuntimeException("User not found");

        user.getJournalEntries().removeIf(x -> x.getId().equals(id));
        userService.saveUser(user);

        journalEntryRepository.deleteById(id);
    }
}
