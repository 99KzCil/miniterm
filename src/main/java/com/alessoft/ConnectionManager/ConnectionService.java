package com.alessoft.ConnectionManager;

import java.util.Map;
import com.alessoft.LoginManager.Utils.AES;
import com.alessoft.LoginManager.Utils.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ConnectionService {

    @Autowired
    private ConnectionRepo connectionRepo;

    @Autowired
    private Jwt jwt;

    public ResponseEntity<String> newConnection(String connectionId) {
        try {
            String username = jwt.getUserNameFromJwtCookie();
                

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ResponseEntity<String> save(Map<String, String> params) {
        try {
            // throws if not logged in
            jwt.getBody();
            Connection connection = new Connection();
            connection.setId(params.get("id"));
            connection.setType("ssh");
            connection.setName(params.get("name"));
            connection.setHost(params.get("host"));
            connection.setPort(Integer.parseInt(params.get("port")));
            connection.setUsername(params.get("username"));
            connection.setPassword(AES.encode(params.get("password")));
            connection.setPublicKey(AES.encode(params.get("publickey")));
            connectionRepo.save(connection);
            return ResponseEntity.ok("");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public ResponseEntity<Object> get() throws Exception {
        // throws if not logged in
        jwt.getBody();

        Iterable<Connection> connections = connectionRepo.findAll();
        connections.forEach(connection -> {
            connection.setPassword(null);
        });
        return ResponseEntity.ok(connections);
    }

    public ResponseEntity<String> remove(Map<String, String> params) throws Exception {
        // throws if not logged in
        jwt.getBody();

        Connection connection = new Connection();
        connection.setId(params.get("id"));
        connectionRepo.delete(connection);
        return ResponseEntity.ok("");
    }



}
