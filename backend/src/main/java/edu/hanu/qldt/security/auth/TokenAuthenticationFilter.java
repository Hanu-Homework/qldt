package edu.hanu.qldt.security.auth;

import edu.hanu.qldt.security.TokenHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TokenAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    TokenHelper tokenHelper;

    @Autowired
    UserDetailsService userDetailsService;

    public static final String ROOT_MATCHER = "/";
    public static final String HTML_MATCHER = "/**/*.html";
    public static final String CSS_MATCHER = "/**/*.css";
    public static final String LOGIN_MATCHER = "/auth/login";
    public static final String LOGOUT_MATCHER = "/auth/logout";

    private final List<String> pathsToSkip = Arrays.asList(
            ROOT_MATCHER,
            HTML_MATCHER,
            CSS_MATCHER,
            LOGIN_MATCHER,
            LOGOUT_MATCHER
    );

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        String authToken = tokenHelper.getToken(httpServletRequest);
        if (authToken != null && !skipPathRequest(httpServletRequest, pathsToSkip)) {
            try {
                String username = tokenHelper.getUsernameFromToken(authToken);
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                TokenBasedAuthentication authentication = new TokenBasedAuthentication(userDetails);
                authentication.setToken(authToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                SecurityContextHolder.getContext().setAuthentication(new AnonAuthentication());
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private boolean skipPathRequest(HttpServletRequest request, List<String> pathsToSkip ) {
        Assert.notNull(pathsToSkip, "path cannot be null.");
        List<RequestMatcher> m = pathsToSkip.stream().map(path -> new AntPathRequestMatcher(path)).collect(Collectors.toList());
        OrRequestMatcher matchers = new OrRequestMatcher(m);
        return matchers.matches(request);
    }
}
