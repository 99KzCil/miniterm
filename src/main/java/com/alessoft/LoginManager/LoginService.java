package com.alessoft.LoginManager;

import java.util.Map;
import com.alessoft.LoginManager.Model.User;
import com.alessoft.LoginManager.Repo.UserRepo;
import com.alessoft.LoginManager.Utils.AES;
import com.alessoft.LoginManager.Utils.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class LoginService {

    @Autowired
    private UserRepo repo;

    @Autowired
    private Jwt jwt;

    public ResponseEntity<Object> login(Map<String, String> params) {
        try {
            checkFields(params);
            String username = params.get("username");
            String password = params.get("password");
            User user = repo.findByUsername(username);
            if (!AES.matches(password, user.getPassword())) throw new Exception("failed");
            jwt.setJwtCookie(user);
            user.setPassword(null);
            return ResponseEntity.ok().body(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    private void checkFields(Map<String, String> params) throws Exception {
        if (!params.containsKey("username") || !params.containsKey("password")) throw new Exception("check fields");
        if (params.get("username").length() < 3 || params.get("password").length() < 3) throw new Exception("check fields.");
    }

    public ResponseEntity<Object> check() {
        try {
            Map<String, Object> body = jwt.getBody();
            User user = (User) repo.findByUsername(body.get("username").toString());
            user.setPassword(null);
            jwt.setJwtCookie(user); // renew expire date
            return ResponseEntity.ok().body(user);
        } catch (Exception e) {
            jwt.deleteCookie();
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    public ResponseEntity<Object> logout() {
        jwt.deleteCookie();
        return ResponseEntity.ok().build();
    }

}
