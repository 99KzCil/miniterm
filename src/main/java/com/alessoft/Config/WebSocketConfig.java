package com.alessoft.Config;

import com.alessoft.SSHManager.SSHSocketHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.PerConnectionWebSocketHandler;


@Configuration
@EnableWebSocket
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new PerConnectionWebSocketHandler(SSHSocketHandler.class), "/ssh")//
                .setAllowedOrigins("localhost", "https://miniterm.enesaltinkaya.com");
        // .addInterceptors(new SSHSocketHandshakeHandler());
    }

}
