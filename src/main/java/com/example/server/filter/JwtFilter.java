package com.example.server.filter;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.example.server.expection.TokenInvalidException;
import com.example.server.service.JwtService;
import com.example.server.service.UserService;
import com.example.server.service.UserTokenService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

  private final JwtService jwtService;
  private final UserService userService;
  private final UserTokenService userTokenService;
  private final HandlerExceptionResolver handlerExceptionResolver;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    try {
      final String authHeader = request.getHeader("Authorization");

      if (authHeader == null || !authHeader.startsWith("Bearer ")) {
        filterChain.doFilter(request, response);
        return;
      }

      final String token = authHeader.substring(7);

      if (!userTokenService.isTokenValid(token)) {
        throw new TokenInvalidException("Invalid token.");
      }

      final String username = jwtService.extractUsername(token);
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

      if (username != null && authentication == null) {
        UserDetails userDetails = userService.loadUserByUsername(username);

        if (jwtService.validateToken(token, userDetails.getUsername())) {
          UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
              userDetails, null, userDetails.getAuthorities() //
          );
          SecurityContextHolder.getContext().setAuthentication(authToken);
        }
      }
      filterChain.doFilter(request, response);
    } catch (Exception ex) {
      handlerExceptionResolver.resolveException(request, response, null, ex);
      return;
    }
  }

}
