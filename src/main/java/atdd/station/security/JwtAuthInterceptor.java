package atdd.station.security;

import atdd.station.domain.UserRepository;
import atdd.station.dto.user.FindByEmailResponseView;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@AllArgsConstructor
public class JwtAuthInterceptor extends HandlerInterceptorAdapter {
    public static final String AUTH_USER_KEY = "user";
    private TokenAuthenticationService tokenAuthenticationService;
    private UserRepository userRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authorization == null) {
            throw new AuthenticationException("Header 에 token 이 존재하지 않습니다.");
        }

        if (!tokenAuthenticationService.isVerifyToken(authorization)) {
            throw new AuthenticationException("Not invalid Token!");
        }

        String email = tokenAuthenticationService.getEmailByJwt(authorization);
        if (email == null) {
            return false;
        }

        FindByEmailResponseView user = userRepository.findByEmail(email);
        request.setAttribute(AUTH_USER_KEY, user.toEntity());

        return true;
    }
}
