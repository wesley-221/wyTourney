package wybin.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import wybin.api.models.authentication.AuthRoute;
import wybin.api.models.authentication.JWToken;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@Component
public class HttpInterceptor extends OncePerRequestFilter {
	@Value("${jwt.passphrase}")
	private String passphrase;

	private final AuthRoute[] authRoutes = new AuthRoute[] {

	};

	@Override
	protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
		// Check if authentication is needed
		if(this.needsAuthorization(httpServletRequest.getServletPath()) && !HttpMethod.OPTIONS.matches(httpServletRequest.getMethod())) {
			AuthRoute authRoute = getAuthorizationRoute(httpServletRequest.getServletPath());
			JWToken jwToken = getJWToken(httpServletRequest);

			// Check if the token was set
			if(jwToken == null) {
				httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "You have to be authenticated in order to use this.");
				return;
			}

			// Check if the user has administrator permission
			if(authRoute.isRequiresAdmin() && !jwToken.isAdmin()) {
				httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "You have to be an administrator in order to use this.");
				return;
			}
		}

		filterChain.doFilter(httpServletRequest, httpServletResponse);
	}

	private AuthRoute getAuthorizationRoute(String url) {
		return Arrays.stream(authRoutes).filter(authRoute -> url.contains(authRoute.getUrl())).findFirst().orElse(null);
	}

	private boolean needsAuthorization(String url) {
		return Arrays.stream(authRoutes).anyMatch(authRoute -> url.contains(authRoute.getUrl()));
	}

	private JWToken getJWToken(HttpServletRequest request) {
		try {
			String token = request.getHeader(HttpHeaders.AUTHORIZATION);
			return JWToken.decode(token, this.passphrase);
		}
		catch (NullPointerException e) {
			return null;
		}
	}
}
