package com.alessoft.LoginManager.Utils;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.alessoft.LoginManager.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class Jwt {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    public static final byte[] theKey = "¯¯\\(°_o)//¯¯((i dont*know what the_secret_is))¯¯\\(o_°)//¯¯".getBytes(StandardCharsets.UTF_8);

    public String getUserNameFromJwtCookie() throws Exception {
        try {
            return Jwts.parser().setSigningKey(theKey).parseClaimsJws(getCookie().getValue()).getBody().get("username").toString();
        } catch (Exception e) {
            throw new Exception("No user logged in!");
        }
    }

    public Cookie getCookie() throws Exception {
        for (Cookie cook : request.getCookies())
            if (cook.getName().equals("access_token"))
                return cook;
        throw new Exception("No logged user!");
    }

    public void setJwtCookie(User dbUser) {
        Map<String, Object> body = setBody(dbUser);
        String jwt = Jwts.builder().setClaims(body).signWith(SignatureAlgorithm.HS512, theKey).compact();
        Cookie cookie = new Cookie("access_token", jwt);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24 * 30);
        response.addCookie(cookie);
    }

    private Map<String, Object> setBody(User dbUser) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", dbUser.getUsername());
        // claims.put("name", dbUser.getName());
        // claims.put("iat", System.currentTimeMillis());
        return claims;
    }

    public Map<String, Object> getBody() throws Exception {
        return Jwts.parser().setSigningKey(theKey).parseClaimsJws(getCookie().getValue()).getBody();
    }

    public void deleteCookie() {
        Cookie cookie = new Cookie("access_token", "");
        cookie.setPath("/");
        cookie.setSecure(true);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}