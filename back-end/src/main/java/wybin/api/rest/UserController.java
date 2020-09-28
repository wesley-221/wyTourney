package wybin.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import wybin.api.models.authentication.JWToken;
import wybin.api.models.authentication.User;
import wybin.api.models.helpers.MeHelper;
import wybin.api.repositories.UserRepository;

import java.net.URI;

@RestController
public class UserController {
    @Autowired
    UserRepository userRepository;

    @Value("${jwt.passphrase}")
    private String passphrase;

    @GetMapping("/user")
    public ResponseEntity<Object> getAllusers(@RequestHeader(value = "Authorization", required = false) String token, @RequestHeader(value = "OsuAuthorization", required = false) String osuToken) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();

        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).location(uri).body("No token provided.");
        }

        JWToken jwToken = JWToken.decode(token, passphrase);

        if (jwToken == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).location(uri).body("Invalid token provided.");
        }

        User user = userRepository.findById(jwToken.getUserId());

        if (user == null) {
            return ResponseEntity.notFound().location(uri).build();
        }

        MeHelper meHelper = (MeHelper) OsuController.getOsuApiData("me", MeHelper.class, osuToken);
        user.updateFromMeHelper(meHelper);

        userRepository.save(user);

        return ResponseEntity.ok().body(user);
    }
}
