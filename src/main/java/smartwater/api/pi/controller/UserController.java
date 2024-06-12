package smartwater.api.pi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import smartwater.api.pi.domain.users.User;
import smartwater.api.pi.domain.users.UserRegistration;
import smartwater.api.pi.domain.users.UserRegistrationData;
import smartwater.api.pi.domain.users.UserRepository;

@RestController
@RequestMapping("/api/timeseries/v0.5/smartcampusmaua/user")
public class UserController {
    
    @Autowired
    private UserRepository repository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @SuppressWarnings("rawtypes")
    @Transactional
    @PostMapping("register")
    public ResponseEntity registerUser(@RequestBody UserRegistration userRegistry, UriComponentsBuilder uriBuilder) {
        try {
            if(userRegistry != null) {
                var encryptedPassword = bCryptPasswordEncoder.encode(userRegistry.password());
                var user = new User(new UserRegistration(userRegistry.email(), encryptedPassword, userRegistry.user_role()));
                repository.save(user);

                var uri = uriBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri();
                return ResponseEntity.created(uri).body(new UserRegistrationData(user));
            }            
            return ResponseEntity.badRequest().body("THe body containing the user is missing!");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
