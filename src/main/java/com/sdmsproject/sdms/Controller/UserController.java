package com.sdmsproject.sdms.Controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;

import com.sdmsproject.sdms.Service.UserService;
import com.sdmsproject.sdms.model.User;

@RestController
@CrossOrigin(origins = "http://localhost:8080") // Adjust as necessary
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/addTeacher")
    public ResponseEntity<String> addTeacher(@RequestBody User user) {
        if (user == null) {
            System.out.println("User Data is missing");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User data is missing");
        }

        ResponseEntity<String> response = userService.createUser(user);

        // Log the status or any necessary details
        System.out.println("User added: " + user);

        return response;
    }
}
