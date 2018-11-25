package com.alessoft.SessionManager;

import java.util.UUID;
import com.alessoft.ConnectionManager.Connection;
import com.alessoft.SSHManager.SSHService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.web.socket.WebSocketSession;
import lombok.Data;

@Data
public class Session {
    private int order;
    private String id = UUID.randomUUID().toString();
    private Connection connection;
    private String state = "stopped";
    private boolean active = false;

    @JsonIgnore
    private SSHService sshService = new SSHService();
    @JsonIgnore
    private WebSocketSession webSocketSession;
}
