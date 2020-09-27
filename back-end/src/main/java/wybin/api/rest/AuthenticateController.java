package wybin.api.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import wybin.api.models.authentication.OsuOauthHelper;

@RestController
public class AuthenticateController {
    @Value("${osu.oauth.client_id}")
    private String oauthClientId;

    @Value("${osu.oauth.client_secret}")
    private String oauthClientSecret;

    @Value("${osu.oauth.redirect_uri}")
    private String oauthRedirectUri;

    @PostMapping("/request-osu-token")
    public ResponseEntity<Object> requestOsuToken(@RequestBody String osuOauthToken) {
        final String OSU_OAUTH_URL = "https://osu.ppy.sh/oauth/token";

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();

        requestBody.add("client_id", this.oauthClientId);
        requestBody.add("client_secret", this.oauthClientSecret);
        requestBody.add("code", osuOauthToken);
        requestBody.add("grant_type", "authorization_code");
        requestBody.add("redirect_uri", this.oauthRedirectUri);

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

        return ResponseEntity.ok(requestToken);
    }
}
