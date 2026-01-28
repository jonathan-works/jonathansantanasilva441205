package br.gov.mt.seplag.seletivo.ArtistHub.config;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
            .addFilterBefore(new SameOriginEnforcementFilter(), CorsFilter.class);
        return http.build();
    }

    static class SameOriginEnforcementFilter extends OncePerRequestFilter {
        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
                throws ServletException, IOException {
            String origin = request.getHeader("Origin");
            if (origin != null && !origin.isBlank()) {
                String expectedOrigin = buildExpectedOrigin(request);
                if (expectedOrigin != null && !origin.equalsIgnoreCase(expectedOrigin)) {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.setContentType("text/plain;charset=UTF-8");
                    response.getWriter().write("Cross-origin bloqueado: origem não permitida");
                    return;
                }
            }
            filterChain.doFilter(request, response);
        }

        private String buildExpectedOrigin(HttpServletRequest request) {
            String forwardedProto = headerOrNull(request, "X-Forwarded-Proto");
            String forwardedHost = headerOrNull(request, "X-Forwarded-Host");

            if (forwardedHost != null) {
                String scheme = forwardedProto != null ? forwardedProto : request.getScheme();
                return scheme + "://" + forwardedHost;
            }

            String scheme = request.getScheme();
            String hostHeader = headerOrNull(request, "Host");
            if (hostHeader != null) {
                return scheme + "://" + hostHeader;
            }

            String host = request.getServerName();
            int port = request.getServerPort();
            String portPart = isDefaultPort(scheme, port) ? "" : (":" + port);
            return scheme + "://" + host + portPart;
        }

        private boolean isDefaultPort(String scheme, int port) {
            return ("http".equalsIgnoreCase(scheme) && port == 80) || ("https".equalsIgnoreCase(scheme) && port == 443);
        }

        private String headerOrNull(HttpServletRequest request, String name) {
            String value = request.getHeader(name);
            return (value == null || value.isBlank()) ? null : value;
        }
    }
}