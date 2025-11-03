package yash.SpringProject.JournalApp.Controller;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import yash.SpringProject.JournalApp.Entity.JournalEntry;
import yash.SpringProject.JournalApp.Entity.User;
import yash.SpringProject.JournalApp.service.UserService;
import yash.SpringProject.JournalApp.service.journalEntryService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerv2 {

    @Autowired
    private journalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    // ✅ Create journal entry
    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            journalEntryService.saveEntry(myEntry, userName);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // ✅ Get all journal entries of logged-in user
    @GetMapping
    public ResponseEntity<?> getAllJournalEntriesOfUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);

        List<JournalEntry> all = user.getJournalEntries();
        if (all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // ✅ Get specific journal entry by ID (only if owned by user)
    @GetMapping("/id/{myId}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId myId) {
        try {
            System.out.println("✅ Inside getJournalEntryById with id: " + myId);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            User user = userService.findByUserName(userName);

            boolean hasEntry = user.getJournalEntries().stream()
                    .anyMatch(x -> x.getId().equals(myId));

            if (hasEntry) {
                Optional<JournalEntry> journalEntry = journalEntryService.findById(myId);
                if (journalEntry.isPresent()) {
                    return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
                }
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ✅ Delete by ID (user must be owner)
    @DeleteMapping("/id/{myId}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId myId) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            journalEntryService.deleteById(myId, userName);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // ✅ Update journal entry (user must be owner)
    @PutMapping("/id/{myId}")
    public ResponseEntity<JournalEntry> updateJournalEntryById(
            @PathVariable ObjectId myId,
            @RequestBody JournalEntry newEntry) {
        try {
            System.out.println("✅ Inside getJournalEntryById with id: " + myId);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();

            JournalEntry old = journalEntryService.findById(myId).orElse(null);
            if (old != null) {
                old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().isEmpty() ? newEntry.getTitle() : old.getTitle());
                old.setContent(newEntry.getContent() != null && !newEntry.getContent().isEmpty() ? newEntry.getContent() : old.getContent());

                journalEntryService.saveEntry(old, userName);
                return new ResponseEntity<>(old, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
