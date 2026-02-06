package br.gov.mt.seplag.seletivo.ArtistHub.config;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.reflect.Type;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import br.gov.mt.seplag.seletivo.ArtistHub.config.security.JwtService;
import br.gov.mt.seplag.seletivo.ArtistHub.model.Usuario;
import br.gov.mt.seplag.seletivo.ArtistHub.repository.UsuarioRepository;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WebSocketSecurityTest {

    @LocalServerPort
    private int port;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    private WebSocketStompClient stompClient;
    private String token;

    @BeforeEach
    void setUp() {
        stompClient = new WebSocketStompClient(new SockJsClient(
                List.of(new WebSocketTransport(new StandardWebSocketClient()))));
        stompClient.setMessageConverter(new StringMessageConverter());

        usuarioRepository.deleteAll();
        Usuario user = Usuario.builder()
                .login("testuser")
                .senha(passwordEncoder.encode("password"))
                .build();
        usuarioRepository.save(user);
        token = jwtService.generateToken(user);
    }

    @Test
    void shouldConnectWithValidToken() throws Exception {
        StompHeaders headers = new StompHeaders();
        headers.add("Authorization", "Bearer " + token);

        StompSession session = stompClient
                .connectAsync("ws://localhost:" + port + "/ws", (WebSocketHttpHeaders) null, headers, new StompSessionHandlerAdapter() {})
                .get(5, SECONDS);

        assertThat(session.isConnected()).isTrue();
    }

    @Test
    void shouldFailConnectWithoutToken() {
        StompHeaders headers = new StompHeaders();
        
        Exception exception = assertThrows(ExecutionException.class, () -> {
            stompClient
                .connectAsync("ws://localhost:" + port + "/ws", (WebSocketHttpHeaders) null, headers, new StompSessionHandlerAdapter() {})
                .get(5, SECONDS);
        });
        
        // Usually checks for connection closed or similar error
        assertThat(exception.getMessage()).containsAnyOf("Connection closed", "Connection lost", "closed", "failed");
    }
}
