package yash.SpringProject.JournalApp.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import yash.SpringProject.JournalApp.Entity.User;
import yash.SpringProject.JournalApp.service.UserService;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @GetMapping("/health-check")
    public String healthCheck(){
        return "OK";
    }

    @PostMapping("/create-user")
    public void createUser(@RequestBody User user){
        userService.saveEntry(user);
    }
}
