package com.alessoft.SSHManager;

import com.alessoft.SessionManager.SessionService;
import org.json.JSONObject;
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
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {
        JSONObject data = new JSONObject(webSocketMessage.getPayload().toString());
        String username = webSocketSession.getAttributes().get("username").toString();

        switch (data.getString("command")) {
            case "activateSSH":
                sessionService.activateSSH(username, data.getString("sessionId"), data.getInt("rows"), data.getInt("cols"), webSocketSession);
                break;
            case "setPtySizeSSH":
                sessionService.setPtySizeSSH(username, data.getString("sessionId"), data.getInt("rows"), data.getInt("cols"));
                break;
            case "keySSH":
                sessionService.keySSH(username, data.getString("sessionId"), data.getString("key"));
                break;
            case "sendCacheSSH":
                sessionService.sendCacheSSH(username, data.getString("sessionId"), data.getInt("rows"), data.getInt("cols"), webSocketSession);
                break;
            default:
                System.out.println(String.format("not handled , %s", data.toString(4)));
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
