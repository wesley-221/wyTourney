package wybin.api.rest;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import wybin.api.models.helpers.MeHelper;

@RestController
public class OsuController {
    private static final String OSU_API_URL = "https://osu.ppy.sh/api/v2/";

    /**
     * Get data from the osu v2 api
     *
     * @param endpoint    the endpoint to get the data from
     * @param returnClass the type of the class what the data will be parsed to
     * @param token       the token retrieved from oauth process
     * @return parsed data from the endpoint
     */
    public static Object getOsuApiData(String endpoint, Class<?> returnClass, String token) {
        return WebClient
                .create(OSU_API_URL + endpoint)
                .get()
                .header("Authorization", token)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(returnClass)
                .onErrorMap(e -> e)
                .blockLast();
    }

    @GetMapping("osu/me")
    public ResponseEntity<Object> getMeData(@RequestHeader(value = "OsuAuthorization", required = false) String osuToken) {
        var meData = getOsuApiData("me", MeHelper.class, osuToken);
        return ResponseEntity.ok(meData);
    }
}
