package edu.hanu.qldt.controller;

import edu.hanu.qldt.security.TokenHelper;
import edu.hanu.qldt.service.auth.impl.CustomUserDetailsService;
import edu.hanu.qldt.model.user.UserTokenState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * The {@link AuthenticationController} contains all rest api function that need to
 * manage the User authentication.
 *
 * @see CustomUserDetailsService
 */
@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

    private final CustomUserDetailsService userDetailsService;

    private final TokenHelper tokenHelper;

    @Value("${jwt.expires_in}")
    private int EXPIRES_IN;

    @Value("${jwt.cookie}")
    private String TOKEN_COOKIE;

    @Autowired
    public AuthenticationController(
            CustomUserDetailsService userDetailsService,
            TokenHelper tokenHelper
    ) {
        this.userDetailsService = userDetailsService;
        this.tokenHelper = tokenHelper;
    }

    @GetMapping(value = "/refresh")
    public ResponseEntity<?> refreshAuthenticationToken(HttpServletRequest request, HttpServletResponse response) {

        String authToken = tokenHelper.getToken(request);
        if (authToken != null && tokenHelper.canTokenBeRefreshed(authToken)) {
            String refreshedToken = tokenHelper.refreshToken(authToken);

            Cookie authCookie = new Cookie(TOKEN_COOKIE, (refreshedToken));
            authCookie.setPath("/");
            authCookie.setHttpOnly(true);
            authCookie.setMaxAge(EXPIRES_IN);
            response.addCookie(authCookie);

            UserTokenState userTokenState = new UserTokenState(refreshedToken, EXPIRES_IN);
            return ResponseEntity.ok(userTokenState);
        } else {
            UserTokenState userTokenState = new UserTokenState();
            return ResponseEntity.accepted().body(userTokenState);
        }
    }

    @PostMapping(value = "/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody PasswordChanger passwordChanger) {
        userDetailsService.changePassword(passwordChanger.oldPassword, passwordChanger.newPassword);
        Map<String, String> result = new HashMap<>();
        result.put("result", "success");
        return ResponseEntity.accepted().body(result);
    }

    static class PasswordChanger {
        public String oldPassword;
        public String newPassword;
    }
}
