package com.linked.matched.config;


import com.linked.matched.config.websocket.ChatHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class StompWebSocketConfig implements WebSocketConfigurer  {

    private final ChatHandler chatHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(chatHandler,"/stomp/chat")
                .setAllowedOriginPatterns("http://*.*.*.*:8080")
                .withSockJS();
    }
}
