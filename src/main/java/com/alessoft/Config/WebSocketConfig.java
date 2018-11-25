package com.alessoft.Config;

import com.alessoft.SSHManager.SSHSocketHandshakeHandler;
import com.alessoft.SSHManager.SocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;


@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Autowired
    private SocketHandler socketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(socketHandler, "/api/ssh")//
                .setAllowedOrigins("*")//
                .addInterceptors(new SSHSocketHandshakeHandler())//
                .withSockJS().setHeartbeatTime(10000);
    }
}
