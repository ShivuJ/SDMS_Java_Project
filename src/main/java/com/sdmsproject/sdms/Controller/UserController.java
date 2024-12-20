package com.sdmsproject.sdms.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

        ResponseEntity<String> response = userService.createUser(userData);

        // Log the status or any necessary details
        System.out.println("User added: " + userData);

        return response;
    }
    
    @GetMapping("teacher")
    public List<UserEntity> readUsers() {
        return userService.readAllUsers();
    }
    
    @PostMapping("/deleteUser")
    public ResponseEntity<String> inactivateUser(@RequestBody Long id) {
    	ResponseEntity<String> response = userService.inactivateUser(id);
    	 return response;
    }
    
//    @PostMapping("/updateUser/{id}")
//    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody UserEntity user) {
//    	ResponseEntity<String> response = userService.updateUser(id, user);
//    	return response;
//    }
    
    @GetMapping("/editUser/{id}")
    public ResponseEntity<UserEntity> readUserById(@PathVariable Long id) {
    	ResponseEntity<UserEntity> response = userService.readUserById(id);
    	return response;
    }
    
}
