package wybin.api.rest;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import wybin.api.models.JsonViews;
import wybin.api.models.authentication.JWToken;
import wybin.api.models.authentication.User;
import wybin.api.repositories.UserRepository;

import java.util.HashMap;

@RestController
public class AuthenticateController {
	@Autowired
	UserRepository userRepository;

	private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@Value("${jwt.issuer}")
	private String issuer;

	@Value("${jwt.passphrase}")
	private String passphrase;

	@PostMapping("/register")
	@JsonView(JsonViews.ShowUser.class)
	public ResponseEntity<Object> register(@RequestBody User user) {
		HashMap<String, Object> message = new HashMap<>();

		if(userRepository.existsByUsername(user.getUsername())) {
			message.put("message", "The username " + user.getUsername() + " is already in use.");
			message.put("username", user.getUsername());

			return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
		}

		if(!user.getPassword().equals(user.getPasswordConfirm())) {
			message.put("message", "The passwords you have entered do not match.");

			return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
		}

		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);

		message.put("message", "Successfully registered your account with the username " + user.getUsername() + ".");
		message.put("user", user);

		return ResponseEntity.status(HttpStatus.CREATED).body(message);
	}

	@PostMapping("/login")
	@JsonView(JsonViews.ShowUser.class)
	public ResponseEntity<Object> login(@RequestBody User user) {
		User findUser = userRepository.findByUsername(user.getUsername());
		HashMap<String, Object> message = new HashMap<>();

		// Check if the user exists
		if(findUser == null) {
			message.put("message", "Invalid username or password given.");
			return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
		}

		// Check if the correct password is given
		if(!passwordEncoder.matches(user.getPassword(), findUser.getPassword())) {
			message.put("message", "Invalid username or password given.");
			return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
		}

		JWToken jwToken = new JWToken(findUser.getId(), findUser.getUsername(), findUser.isAdmin());
		String token = jwToken.encode(this.issuer, this.passphrase);

		message.put("message", "Successfully logged in.");
		message.put("user", findUser);
		message.put("token", token);

		return new ResponseEntity<>(message, HttpStatus.OK);
	}
}
