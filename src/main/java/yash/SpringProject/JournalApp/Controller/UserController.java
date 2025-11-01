package yash.SpringProject.JournalApp.Controller;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yash.SpringProject.JournalApp.Entity.JournalEntry;
import yash.SpringProject.JournalApp.Entity.User;
import yash.SpringProject.JournalApp.service.UserService;
import yash.SpringProject.JournalApp.service.journalEntryService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")

public class UserController {

     @Autowired
     private UserService userService;

     @GetMapping
     public List<User> gettAllUsers(){
         return userService.getAll();
    }
    @PostMapping
    public void createUser(@RequestBody User user){
         userService.saveEntry(user);
    }

    @PutMapping("/{userName}")
    public ResponseEntity<?> updateUser(@RequestBody User user,@PathVariable String userName){
         User userIndB = userService.findByUserName(userName);
         if(userIndB!=null){
             userIndB.setUserName(user.getUserName());
             userIndB.setPassword(user.getPassword());
             userService.saveEntry(userIndB);
         }
         return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

