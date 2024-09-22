package com.sdmsproject.sdms.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sdmsproject.sdms.Service.UserService;
import com.sdmsproject.sdms.model.UserEntity;

@RestController // Adjust as necessary
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/addTeacher")
    public ResponseEntity<String> addTeacher(@RequestBody UserEntity userData) {
//        if (user == null) {
//            System.out.println("User Data is missing");
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User data is missing");
//        }

        ResponseEntity<String> response = userService.createUser(userData);

        // Log the status or any necessary details
        System.out.println("User added: " + userData);

        return response;
    }
    
    @GetMapping("teacher")
    public List<UserEntity> readUsers() {
        return userService.readAllUsers();
    }
    
}
