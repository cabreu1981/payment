package com.getontop.clients.user;


import com.getontop.clients.user.models.User;
import com.getontop.clients.user.models.requestObjects.UserRegistrationRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        value = "user-accounts",
        path = "api/v1/users"

)
public interface UserClient {


    @PostMapping
    void createUser(@RequestBody UserRegistrationRequest userRegistrationRequest);

    @GetMapping("/{accountNumber}")
    ResponseEntity<User> getUserByAccountNumber(@PathVariable("accountNumber") String accountNumber);

    @GetMapping("/user/{nationalId}")
    ResponseEntity<User> getUserByNationalId(@PathVariable("nationalId") String nationalId);
}
