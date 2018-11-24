package com.alessoft.Config;

import com.alessoft.SSHManager.SSHSocketHandler;
import com.alessoft.SSHManager.SSHSocketHandshakeHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.PerConnectionWebSocketHandler;


@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(getHandler(), "/api/ssh")//
                .setAllowedOrigins("*").addInterceptors(new SSHSocketHandshakeHandler());
    }

    @Bean
    public PerConnectionWebSocketHandler getHandler() {
        return new PerConnectionWebSocketHandler(SSHSocketHandler.class);
    }
}
