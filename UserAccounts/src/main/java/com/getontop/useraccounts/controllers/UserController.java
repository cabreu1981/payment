package com.getontop.useraccounts.controllers;

import com.getontop.useraccounts.exceptions.UserAlreadyExistsException;
import com.getontop.useraccounts.models.User;
import com.getontop.useraccounts.models.requestObjects.UserRegistrationRequest;
import com.getontop.useraccounts.repository.UserRepository;
import com.getontop.useraccounts.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/v1/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    // Create a new user
    @PostMapping
    public void createUser(@RequestBody UserRegistrationRequest userRegistrationRequest) {
        log.info("Creating user: {}", userRegistrationRequest);
        if (userRepository.findByNationalId(userRegistrationRequest.nationalId()).isPresent()) {
            log.info("user with nationalID {} already exists", userRegistrationRequest.nationalId());
            throw new UserAlreadyExistsException("user with nationalId " + userRegistrationRequest.nationalId() + " already exists");
        }
        userService.registerUser(userRegistrationRequest);
    }

    @GetMapping("/user/{nationalId}")
    public ResponseEntity<User> getUserByNationalId(@PathVariable("nationalId") String nationalId) {
        return userService.getUserByNationalId(nationalId);
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity getUserByAccountNumber(@PathVariable("accountNumber") String accountNumber) {
        return userService.getUserByAccountNumber(accountNumber);
    }


/*

    // Get all users
    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Get a user by ID
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(" user with id " + id + " does not exist"));
    }

    // Update a user by ID
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Integer id, @RequestBody UserRegistrationRequest updatedUser) {
        return userRepository.findById(id).map(user -> {
            user.setName(updatedUser.name());
            user.setNationalId(updatedUser.nationalId());
            user.setBankName(updatedUser.bankName());
            user.setAccountNumber(updatedUser.accountNumber());
            user.setRoutingNumber(updatedUser.routingNumber());
            return userRepository.save(user);
        }).orElseThrow(() -> new UserNotFoundException(" user with id " + id + " does not exist"));
    }

    // Delete a user by ID
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Integer id) {

        userRepository.deleteById(id);
    }

*/

}
