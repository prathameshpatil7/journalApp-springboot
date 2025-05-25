package com.learnspring.journalApp.controller;

import com.learnspring.journalApp.entity.User;
import com.learnspring.journalApp.services.UserServices;
import org.bson.types.ObjectId;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServices userServices;

    @GetMapping
    public ResponseEntity<?> getAllUsers(){
        List<User> users = userServices.getAllUsers();
        if(!users.isEmpty()){
            return new ResponseEntity<>(users, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No users found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getUserById(@PathVariable ObjectId id){
        Optional<User> user = userServices.getUserById(id);
        if(user.isPresent()){
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No user with this id found", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<String> createUser(@NotNull @RequestBody User user){
        if (userServices.existsByUsername(user.getUsername())) {
            return new ResponseEntity<>("Username already exists", HttpStatus.CONFLICT);
        }
         userServices.saveUser(user);
        return new ResponseEntity<>("User created successfully", HttpStatus.CREATED);
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<String> UpdateUserById(@NotNull @RequestBody User user, @PathVariable ObjectId id){
        Optional<User> existingOpt = userServices.getUserById(id);
        if (existingOpt.isPresent()) {
            User existing = existingOpt.get();
            existing.setUsername(user.getUsername() != null && !user.getUsername().isEmpty() ? user.getUsername() : existing.getUsername());
            existing.setPassword(user.getPassword() != null && !user.getPassword().isEmpty() ? user.getPassword() : existing.getPassword());

            userServices.saveUser(existing);
            return new ResponseEntity<>("User updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/username/{username}")
    public ResponseEntity<String> UpdateUserByUsername(@NotNull @RequestBody User user, @PathVariable String username){
        Optional<User> existingOpt = userServices.getUserByUsername(username);
        if (existingOpt.isPresent()) {
            User existing = existingOpt.get();
            existing.setUsername(user.getUsername() != null && !user.getUsername().isEmpty() ? user.getUsername() : existing.getUsername());
            existing.setPassword(user.getPassword() != null && !user.getPassword().isEmpty() ? user.getPassword() : existing.getPassword());

            userServices.saveUser(existing);
            return new ResponseEntity<>("User updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("/id/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable ObjectId id){
        Optional<User> user = userServices.getUserById(id);
        if(user.isPresent()){
            userServices.deleteUser(id);
            return new ResponseEntity<>("User deleted !", HttpStatus.OK);
        } else{
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }
}
