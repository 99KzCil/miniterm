package com.alessoft.SSHManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SSHService {


    public ResponseEntity<Object> openConnection(String params) {
        JSONObject connection = new JSONObject(params);
        if (connection.getString("type").equals("ssh")) openSSHConnection(connection);
        return null;
    }

    private void openSSHConnection(JSONObject connection) {
        String hostname = connection.getString("hostname");
        int port = connection.getInt("port");
        JSch.setConfig("StrictHostKeyChecking", "no");
        JSch jsch = new JSch();
        try {
            Session session = jsch.getSession("enes", hostname, port);
            session.setPassword("enesenen1");
            
            session.connect();
            Channel channel = session.openChannel("shell");
            InputStream inputStream = channel.getInputStream();
             OutputStream outputStream = channel.getOutputStream();
             
            channel.setInputStream(System.in);
            channel.setOutputStream(System.out);


        } catch (JSchException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }


}
