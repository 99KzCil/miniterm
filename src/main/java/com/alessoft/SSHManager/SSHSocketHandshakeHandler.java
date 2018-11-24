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
        ServletServerHttpRequest httpServletRequest = (ServletServerHttpRequest) serverHttpRequest;
        if (!checkUser(httpServletRequest)) return false;

        System.err.println(serverHttpRequest.getURI());
        System.err.println(serverHttpRequest.getHeaders());

        String[] query = httpServletRequest.getServletRequest().getQueryString().split(",");

        String uuid = query[0];
        long userId = Long.parseLong(query[1]);
        int cols = Integer.parseInt(query[2]);
        int rows = Integer.parseInt(query[3]);

        map.put("uuid", uuid);
        map.put("userId", userId);
        map.put("cols", cols);
        map.put("rows", rows);
        return true;
    }

    private boolean checkUser(ServletServerHttpRequest request) {
        Cookie[] cookies = request.getServletRequest().getCookies();
        String access_token = getAccessToken(cookies);
        if (access_token.isEmpty()) return false;
      
        // parseClaimsJws method throws if access_token is tampered with
        Map<String, Object> body = Jwts.parser().setSigningKey(Jwt.theKey).parseClaimsJws(access_token).getBody();
        System.out.println(body);

        return false;
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
