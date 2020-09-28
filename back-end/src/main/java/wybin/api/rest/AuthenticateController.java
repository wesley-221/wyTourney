package wybin.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import wybin.api.models.Role;
import wybin.api.models.authentication.JWToken;
import wybin.api.models.authentication.User;
import wybin.api.models.helpers.MeHelper;
import wybin.api.models.helpers.OsuLoginHelper;
import wybin.api.models.helpers.OsuOauthHelper;
import wybin.api.repositories.UserRepository;

import java.util.List;

@RestController
public class AuthenticateController {
    @Autowired
    UserRepository userRepository;
    @Value("${osu.oauth.client_id}")
    private String oauthClientId;
    @Value("${osu.oauth.client_secret}")
    private String oauthClientSecret;
    @Value("${osu.oauth.redirect_uri}")
    private String oauthRedirectUri;

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.passphrase}")
    private String passphrase;

    @PostMapping("/osu-authenticate")
    public ResponseEntity<Object> requestOsuToken(@RequestBody String osuOauthToken) {
        final String OSU_OAUTH_URL = "https://osu.ppy.sh/oauth/token";

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();

        requestBody.add("client_id", this.oauthClientId);
        requestBody.add("client_secret", this.oauthClientSecret);
        requestBody.add("code", osuOauthToken);
        requestBody.add("grant_type", "authorization_code");
        requestBody.add("redirect_uri", this.oauthRedirectUri);

        // Request access token
        var requestToken = WebClient
                .create(OSU_OAUTH_URL)
                .post()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromMultipartData(requestBody))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(OsuOauthHelper.class)
                .onErrorMap(e -> e)
                .blockLast();

        // Get /me data from api and parse it to User
        MeHelper meHelper = (MeHelper) OsuController.getOsuApiData("me", MeHelper.class, "Bearer " + requestToken.getAccess_token());
        User user = new User();
        user.updateFromMeHelper(meHelper);
        user.setRole(0);

        // Get existing account if there is one
        User existingAccount = userRepository.findById(user.getId());

        if (existingAccount == null) {
            // Set administrator privileges for these ids
            List<Long> adminIds = List.of(2407265L, 3790227L, 4100941L);

            if (adminIds.contains(user.getId())) {
                user.setRole(Role.ADMINISTRATOR.getValue());
            }
        } else {
            user.setRole(existingAccount.getRole());
        }

        userRepository.save(user);

        JWToken jwToken = new JWToken(user.getId(), user.getRole());
        String token = jwToken.encode(this.issuer, this.passphrase);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, token);

        OsuLoginHelper response = new OsuLoginHelper(requestToken, user);

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }
}
