package com.alessoft.LoginManager;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Object> login(@RequestBody Map<String,String> params) {
        return loginService.login(params);
    }

    @RequestMapping(path = "/check", method = RequestMethod.POST)
    public ResponseEntity<Object> check() {
        return loginService.check();

    }

    @RequestMapping(path = "/logout", method = RequestMethod.POST)
    public ResponseEntity<Object> logout() {
        return loginService.logout();
    }

}
