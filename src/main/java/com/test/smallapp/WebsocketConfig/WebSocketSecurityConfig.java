package com.test.smallapp.WebsocketConfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;
//import org.springframework.security.config.annotation.web.socket.WebSocketMessageBrokerSecurity;

@Configuration
public class WebSocketSecurityConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer {
/*
    @Override
    protected void configureInbound(final WebSocketMessageBrokerSecurity webSocketMessageBrokerSecurity) {
        // Allow WebSocket messages to bypass authentication
        webSocketMessageBrokerSecurity
                .sameOriginDisabled()
                .authorizeAllDestinations();
    }
    */
}
