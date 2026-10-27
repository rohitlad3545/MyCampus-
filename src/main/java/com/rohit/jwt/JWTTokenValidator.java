package com.rohit.jwt;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.rohit.services.MyUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTTokenValidator extends OncePerRequestFilter {

    @Autowired
    private JWTTokenGenerator jwtTokenGenerator;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String requestURI = request.getServletPath();

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())
                || requestURI.equals("/api/v1/register")
                || requestURI.equals("/api/v1/login")) {
            filterChain.doFilter(request, response);
            return;
        }

        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwtToken = header.substring(7).trim();

        if (jwtToken.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String username =
                    jwtTokenGenerator.extractUsername(jwtToken);

            String tokenRole =
                    jwtTokenGenerator.normalizeRole(
                            jwtTokenGenerator.extractRole(jwtToken));

            if (username == null || username.isBlank()
                    || tokenRole == null || tokenRole.isBlank()) {
                SecurityContextHolder.clearContext();
                response.sendError(
                        HttpServletResponse.SC_UNAUTHORIZED,
                        "JWT username or role is missing");
                return;
            }

            if (SecurityContextHolder.getContext()
                    .getAuthentication() == null) {

                UserDetails userDetails =
                        myUserDetailsService.loadUserByUsername(username);

                if (!jwtTokenGenerator.validateToken(
                        jwtToken, userDetails)) {
                    SecurityContextHolder.clearContext();
                    response.sendError(
                            HttpServletResponse.SC_UNAUTHORIZED,
                            "Invalid or expired JWT");
                    return;
                }

                // Use the role stored in the database as the source of truth.
                String databaseRole = userDetails.getAuthorities()
                        .stream()
                        .map(authority -> authority.getAuthority())
                        .filter(role -> role != null && !role.isBlank())
                        .map(jwtTokenGenerator::normalizeRole)
                        .findFirst()
                        .orElse(null);

                System.out.println("JWT USERNAME = " + username);
                System.out.println("JWT ROLE = " + tokenRole);
                System.out.println("DATABASE ROLE = " + databaseRole);

                if (databaseRole == null) {
                    SecurityContextHolder.clearContext();
                    response.sendError(
                            HttpServletResponse.SC_FORBIDDEN,
                            "User has no assigned role");
                    return;
                }

                // Reject stale tokens if the user's role has changed.
                if (!databaseRole.equals(tokenRole)) {
                    SecurityContextHolder.clearContext();
                    response.sendError(
                            HttpServletResponse.SC_FORBIDDEN,
                            "JWT role does not match database role. Please log in again.");
                    return;
                }

                List<SimpleGrantedAuthority> authorities =
                        List.of(new SimpleGrantedAuthority(databaseRole));

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                authorities);

                authentication.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(request));

                SecurityContextHolder.getContext()
                        .setAuthentication(authentication);

                System.out.println(
                        "AUTHENTICATED AUTHORITIES = " + authorities);
            }

        } catch (UsernameNotFoundException exception) {
            SecurityContextHolder.clearContext();
            response.sendError(
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "User associated with JWT was not found");
            return;

        } catch (Exception exception) {
            SecurityContextHolder.clearContext();
            System.out.println(
                    "JWT VALIDATION ERROR = " + exception.getMessage());

            response.sendError(
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "Invalid JWT token");
            return;
        }

        filterChain.doFilter(request, response);
    }
}