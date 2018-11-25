package com.alessoft.ConnectionManager;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/connection")
public class ConnectionController {

    @Autowired
    private ConnectionService connectionService;

    @RequestMapping(path = "save", method = RequestMethod.POST)
    public ResponseEntity<String> save(@RequestBody Map<String, String> params) {
        return connectionService.save(params);
    }

    @RequestMapping(path = "get", method = RequestMethod.GET)
    public ResponseEntity<Object> get() throws Exception {
        return connectionService.get();
    }

    @RequestMapping(path = "remove", method = RequestMethod.POST)
    public ResponseEntity<String> remove(@RequestBody Map<String, String> params) throws Exception {
        return connectionService.remove(params);
    }

}

