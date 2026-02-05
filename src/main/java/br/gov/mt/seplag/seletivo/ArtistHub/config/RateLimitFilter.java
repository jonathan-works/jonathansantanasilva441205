package br.gov.mt.seplag.seletivo.ArtistHub.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Instant;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

@Component
public class RateLimitFilter extends OncePerRequestFilter {

    private final Map<String, Queue<Instant>> requestCounts = new ConcurrentHashMap<>();
    private static final int MAX_REQUESTS_PER_MINUTE = 10;
    private static final long TIME_WINDOW_MILLIS = 60000; // 1 minute

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        String key = getClientKey(request);

        if (isRateLimited(key)) {
            response.setStatus(429); // Too Many Requests
            response.getWriter().write("Limite de requisicoes excedido. Tente novamente mais tarde.");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private String getClientKey(HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getPrincipal())) {
            return authentication.getName();
        }
        return request.getRemoteAddr();
    }

    private boolean isRateLimited(String key) {
        Queue<Instant> requestTimestamps = requestCounts.computeIfAbsent(key, k -> new ConcurrentLinkedQueue<>());
        Instant now = Instant.now();
        Instant windowStart = now.minusMillis(TIME_WINDOW_MILLIS);

        // Remove expired timestamps
        while (!requestTimestamps.isEmpty() && requestTimestamps.peek().isBefore(windowStart)) {
            requestTimestamps.poll();
        }

        if (requestTimestamps.size() < MAX_REQUESTS_PER_MINUTE) {
            requestTimestamps.add(now);
            return false;
        }

        return true;
    }
}