package com.alessoft.SSHManager;

import java.util.Map;
import javax.servlet.http.Cookie;
import com.alessoft.LoginManager.Utils.Jwt;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import io.jsonwebtoken.Jwts;

public class SSHSocketHandshakeHandler implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {
        String username = getUsername(serverHttpRequest);

        // if no user is logged in, dont allow socket connection
        if (username.isEmpty()) return false;
        
        map.put("username", username);
        return true;
    }

    private String getUsername(ServerHttpRequest request) {
        ServletServerHttpRequest httpServletRequest = (ServletServerHttpRequest) request;
        Cookie[] cookies = httpServletRequest.getServletRequest().getCookies();
        String access_token = getAccessToken(cookies);
        if (access_token.isEmpty()) return "";

        // parseClaimsJws method throws if access_token is tampered with
        return Jwts.parser().setSigningKey(Jwt.theKey).parseClaimsJws(access_token).getBody().get("username").toString();
    }

    private String getAccessToken(Cookie[] cookies) {
        String access_token = "";
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("access_token")) {
                access_token = cookie.getValue();
                break;
            }
        }
        return access_token;
    }

    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {

    }
}
