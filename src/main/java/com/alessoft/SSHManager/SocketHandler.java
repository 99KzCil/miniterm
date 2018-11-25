package com.alessoft.SSHManager;

import com.alessoft.SessionManager.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

@Service
public class SocketHandler implements WebSocketHandler {

    @Autowired
    private SessionService sessionService;

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> webSocketMessage) throws Exception {
        String message = webSocketMessage.getPayload().toString();
        String username = session.getAttributes().get("username").toString();
        String[] split = message.split("\\|\\|\\|");
        String sessionId, key;
        int rows, cols;
        switch (split[0]) {
            case "activateSSH":
                sessionId = split[1];
                rows = Integer.parseInt(split[2]);
                cols = Integer.parseInt(split[3]);
                sessionService.activateSSH(username, sessionId, rows, cols, session);
                break;
            case "setPtySizeSSH":
                sessionId = split[1];
                rows = Integer.parseInt(split[2]);
                cols = Integer.parseInt(split[3]);
                sessionService.setPtySizeSSH(username, sessionId, rows, cols);
                break;
            case "keySSH":
                sessionId = split[1];
                key = split[2];
                sessionService.keySSH(username, sessionId, key);
                break;
            case "sendCacheSSH":
                sessionId = split[1];
                rows = Integer.parseInt(split[2]);
                cols = Integer.parseInt(split[3]);
                sessionService.sendCacheSSH(username, sessionId, rows, cols, session);
                break;
            default:
                System.out.println(String.format("not handled , %s", message));
                break;
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {}

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {}

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {}

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
