package com.auction.flab.application.config.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private static final String HEADER_AUTHORIZATION = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";
    private final TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(HEADER_AUTHORIZATION);
        getAccessToken(authorizationHeader).ifPresent(token ->
            tokenProvider.getAuthentication(token).ifPresent(authentication ->
                    SecurityContextHolder.getContext().setAuthentication(authentication)));
        filterChain.doFilter(request, response);
    }

    private Optional<String> getAccessToken(String authorizationHeader) {
        return Optional.ofNullable(authorizationHeader)
                .filter(auth -> auth.startsWith(TOKEN_PREFIX))
                .map(auth -> auth.replaceFirst(TOKEN_PREFIX, "").replaceAll("\s", ""))
                .filter(auth -> !auth.isEmpty())
                .or(Optional::empty);
    }

}
