package yash.SpringProject.JournalApp.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yash.SpringProject.JournalApp.Entity.User;
import yash.SpringProject.JournalApp.service.UserService;

import java.util.List;
import java.util.*;

@RestController
@RequestMapping("/admin")
public class AdminController {


    @Autowired
    private UserService userService;

    @GetMapping("/all-user")
    public ResponseEntity<?> getAllUser(){
        List<User> all =  userService.getAll();
        if(all!=null && !all.isEmpty()){
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
    @PostMapping("/create-admin-user")
    public void createUser(@RequestBody User user){
        userService.saveAdmin(user);
    }
}

