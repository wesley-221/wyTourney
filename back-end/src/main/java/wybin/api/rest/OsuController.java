package wybin.api.rest;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import wybin.api.models.MeHelper;

@RestController
public class OsuController {
    private static String OSU_API_URL = "https://osu.ppy.sh/api/v2/";

    @GetMapping("osu/me")
    public ResponseEntity<Object> getMeData(@RequestHeader(value = "Authorization", required = false) String token) {

        var requestToken = WebClient
                .create(OSU_API_URL + "me")
                .get()
                .header("Authorization", token)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(MeHelper.class)
                .onErrorMap(e -> e)
                .blockLast();

        return ResponseEntity.ok(requestToken);
    }
}
