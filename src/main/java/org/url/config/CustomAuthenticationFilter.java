package org.url.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;
import org.url.repository.UserRepository;

import java.io.IOException;

@Component
@Slf4j
public class CustomAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AppUserDetailService appUserDetailService;

    @Autowired
    private JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            if (request.getHeader("Authorization") != null && request.getHeader("Authorization").startsWith("Bearer ")) {
                String username = jwtService.extractUsername(request.getHeader("Authorization").substring(7));
                if (username != null) {
                    UserDetails userDetails = appUserDetailService.loadUserByUsername(username);
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
            filterChain.doFilter(request, response);
        } catch (ResponseStatusException e) {
            log.error(e.getMessage());
            throw new ResponseStatusException(e.getStatusCode(), e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
