package wybin.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import wybin.api.models.authentication.User;
import wybin.api.repositories.UserRepository;

import java.net.URI;
import java.util.ArrayList;

@RestController
public class UserController {
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Autowired
    UserRepository userRepository;

    @GetMapping("/users")
    public ResponseEntity<ArrayList<User>> getAllusers() {
        ArrayList<User> userArrayList = (ArrayList<User>) userRepository.findAll();

        // Remove password, no need for the end user to know :)
        for (User user : userArrayList) {
            user.setPassword(null);
        }

        return ResponseEntity.ok().body(userArrayList);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        User user = userRepository.findById(id);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();

        if (user == null) {
            return ResponseEntity.notFound().location(uri).build();
        }

        // Remove password, no need for the end user to know
        user.setPassword(null);

        return ResponseEntity.ok().location(uri).body(user);
    }

    @PostMapping("/users")
    public ResponseEntity<Object> updateUser(@RequestBody User user) {
        User getUser = userRepository.findById(user.getId());
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();

        // Check if the user exists
        if (getUser == null) {
            return ResponseEntity.notFound().location(uri).build();
        }

        // Check if the new username exists
        if (!user.getUsername().equals(getUser.getUsername()) && userRepository.existsByUsername(user.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).location(uri).body("{\"message\": \"The username \\\"" + user.getUsername() + "\\\" is already in use.\"}");
        }

        getUser.setUsername(user.getUsername());

        // Check if the password has to be changed
        if (user.getPassword() != null && !user.getPassword().equals("")) {
            if (!user.getPassword().equals(user.getPasswordConfirm())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).location(uri).body("{\"message\": \"The passwords you have entered do not match.\"}");
            }

            getUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        getUser.setAdmin(user.isAdmin());

        userRepository.save(getUser);

        return ResponseEntity.ok().location(uri).body(getUser);
    }
}
