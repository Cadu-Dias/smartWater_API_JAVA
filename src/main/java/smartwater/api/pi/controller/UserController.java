package smartwater.api.pi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import smartwater.api.pi.domain.users.User;
import smartwater.api.pi.domain.users.UserRegistration;
import smartwater.api.pi.domain.users.UserRegistrationData;
import smartwater.api.pi.domain.users.UserRepository;

@Tag(name = "User")
@RestController
@RequestMapping("/api/timeseries/v0.5/smartcampusmaua/user")
@SecurityRequirement(name = "bearer-key")
public class UserController {
    
    @Autowired
    private UserRepository repository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @SuppressWarnings("rawtypes")
    @Transactional
    @GetMapping("search/{email}")
    public ResponseEntity getUser(@PathVariable("email") String email) {
        try {
            if(email != null) {
                User user = repository.findByEmail(email);
                return ResponseEntity.ok(new UserRegistration(user.getEmail(), user.getPassword(), user.getUser_role()));
            }
            return ResponseEntity.badRequest().body("The email property is missing");
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

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

    @SuppressWarnings("rawtypes")
    @PutMapping("update/{email}")
    @Transactional
    public ResponseEntity updateUser(@PathVariable("email") String email, @RequestBody UserRegistration userRegistry) {
        try {
            if(email != null && userRegistry != null) {
                User olderUser = repository.findByEmail(email);
                User user = repository.setUserInfoById(olderUser.getId(), userRegistry.email(), bCryptPasswordEncoder.encode(userRegistry.password()), userRegistry.user_role());
                
                return ResponseEntity.ok(user);
            }
            return ResponseEntity.badRequest().body("The email property or the new body User is missing");
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @SuppressWarnings("rawtypes")
    @DeleteMapping("delete/{email}")
    @Transactional
    public ResponseEntity deleteUser(@PathVariable("email") String email) {
        try {
            if(email != null) {
                User olderUser = repository.findByEmail(email);
                repository.deleteById(olderUser.getId());
                return ResponseEntity.ok(olderUser);
            }
            return ResponseEntity.badRequest().body("The email property is missing");
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
