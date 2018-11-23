package com.alessoft.SSHManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ssh")
public class SSHController {

    @Autowired
    private SSHService sshService;

    @RequestMapping(path = "/open", method = RequestMethod.POST)
    public ResponseEntity<Object> openConnection(@RequestBody String params) {
        return sshService.openConnection(params);
    }

}
