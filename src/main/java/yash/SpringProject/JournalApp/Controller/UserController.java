package yash.SpringProject.JournalApp.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import yash.SpringProject.JournalApp.Entity.User;
import yash.SpringProject.JournalApp.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    // GET /user - get all users (requires authentication)
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAll();
    }



    // POST /user - create a new user
    @PostMapping
    public void createUser(@RequestBody User user) {
        userService.saveEntry(user);
    }


    public User findByUserName(String userName){
        return userService.findByUserName(userName);
    }
    // PUT /user - update logged-in user's info
    // PUT /user - update logged-in user's info
    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInUserName = authentication.getName();

        User userInDb = userService.findByUserName(loggedInUserName);
        if (userInDb != null) {
            // update allowed fields
            if (user.getUserName() != null) userInDb.setUserName(user.getUserName());
            if (user.getPassword() != null && !user.getPassword().isBlank()) userInDb.setPassword(user.getPassword());
            userService.saveEntry(userInDb);
            return ResponseEntity.noContent().build();
        } else {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

    }



}
