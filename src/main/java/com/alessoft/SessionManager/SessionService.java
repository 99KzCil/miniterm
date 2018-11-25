package com.alessoft.SessionManager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import com.alessoft.ConnectionManager.Connection;
import com.alessoft.ConnectionManager.ConnectionRepo;
import com.alessoft.LoginManager.Utils.Jwt;
import com.alessoft.SSHManager.SSHService;
import com.jcraft.jsch.JSchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

@Service
public class SessionService {

    @Autowired
    private Jwt jwt;
    @Autowired
    private ConnectionRepo connectionRepo;
    private final Map<String, Map<String, Session>> sessions = new ConcurrentHashMap<>();

    public ResponseEntity<Object> newSession(String connectionId) throws Exception {
        String username = jwt.getUserNameFromJwtCookie();
        Map<String, Session> userSessions = getUserSessions(username);
        Session session = new Session();
        session.setConnection(getConnection(connectionId));
        session.setOrder(userSessions.size());
        userSessions.put(session.getId(), session);
        return ResponseEntity.ok(session);
    }

    public ResponseEntity<Object> getSessions() throws Exception {
        String username = jwt.getUserNameFromJwtCookie();
        return ResponseEntity.ok(getUserSessions(username));
    }

    public Connection getConnection(String connectionId) {
        Connection connection = connectionRepo.findById(connectionId).orElse(null);

        // dont send private stuff to frontend
        connection.setPassword(null);
        connection.setPrivateKey(null);
        connection.setPrivateKeyPassword(null);
        return connection;
    }

    public Map<String, Session> getUserSessions(String username) {
        Map<String, Session> userSessions = sessions.get(username);
        if (userSessions == null) {
            userSessions = new ConcurrentHashMap<>();
            sessions.put(username, userSessions);
        }
        return userSessions;
    }

    public void activateSSH(String username, String sessionId, int rows, int cols, WebSocketSession webSocketSession) throws JSchException {
        Map<String, Session> userSessions = sessions.get(username);
        for (String key : userSessions.keySet()) {
            userSessions.get(key).setActive(false);
        }

        Session session = userSessions.get(sessionId);
        session.setActive(true);

        session.setWebSocketSession(webSocketSession);
        SSHService sshService = session.getSshService();
        Connection connection = connectionRepo.findById(session.getConnection().getId()).orElse(null);
        sshService.startConnection(session, connection, rows, cols);
    }

    public void keySSH(String username, String sessionId, String key) throws Exception {
        Session session = sessions.get(username).get(sessionId);
        if (session == null) return;
        session.getSshService().send(key);
    }

    public void setPtySizeSSH(String username, String sessionId, int rows, int cols) {
        Session session = sessions.get(username).get(sessionId);
        session.getSshService().setPtySize(rows, cols);
    }

    public void sendCacheSSH(String username, String sessionId, int rows, int cols, WebSocketSession webSocketSession) {
        Map<String, Session> userSessions = sessions.get(username);
        for (String key : userSessions.keySet()) {
            userSessions.get(key).setActive(false);
        }
        Session session = userSessions.get(sessionId);
        session.setActive(true);
        session.setWebSocketSession(webSocketSession);
        SSHService sshService = session.getSshService();
        sshService.sendCache(session);

    }

    public ResponseEntity<Object> remove(String sessionId) throws Exception {
        String username = jwt.getUserNameFromJwtCookie();
        Map<String, Session> userSessions = sessions.get(username);
        Session session = userSessions.get(sessionId);
        session.getSshService().endConnection();
        userSessions.remove(sessionId);
        return ResponseEntity.ok("");
    }
}
