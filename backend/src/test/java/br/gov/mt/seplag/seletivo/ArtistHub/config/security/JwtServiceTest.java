package br.gov.mt.seplag.seletivo.ArtistHub.config.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    @InjectMocks
    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(jwtService, "secret", "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970");
        ReflectionTestUtils.setField(jwtService, "expiration", 300000L); // 5 minutes
    }

    @Test
    void shouldGenerateAndValidateToken() {
        UserDetails user = new User("test@test.com", "password", Collections.emptyList());

        String token = jwtService.generateToken(user);
        assertNotNull(token);

        boolean isValid = jwtService.isTokenValid(token, user);
        assertTrue(isValid);

        String username = jwtService.extractUsername(token);
        assertEquals("test@test.com", username);
    }
}
