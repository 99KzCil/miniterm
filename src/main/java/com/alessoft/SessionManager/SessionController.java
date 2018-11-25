package com.alessoft.SessionManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/session")
public class SessionController {

    @Autowired
    private SessionService sessionService;

    @RequestMapping(path = "getSessions", method = RequestMethod.GET)
    public ResponseEntity<Object> getSessions() throws Exception {
        return sessionService.getSessions();
    }
    @RequestMapping(path = "newSession", method = RequestMethod.POST)
    public ResponseEntity<Object> newSession(@RequestBody String connectionId) throws Exception {
        return sessionService.newSession(connectionId);
    }
}