package com.alessoft.SSHManager;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import com.alessoft.ConnectionManager.Connection;
import com.alessoft.LoginManager.Utils.AES;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import org.json.JSONObject;
import org.springframework.web.socket.TextMessage;

public class SSHService {
    {
        JSch.setConfig("StrictHostKeyChecking", "no");
    }
    private JSch jsch = new JSch();
    private Executor executor = Executors.newCachedThreadPool();
    private Channel channel;
    private Session sshSession;
    private com.alessoft.SessionManager.Session session;
    private String cache = "";
    private int rows, cols;
    private Connection connection;
    private JSONObject data = new JSONObject();

    public void startConnection(com.alessoft.SessionManager.Session session, Connection connection, int rows, int cols) {
        if (channel != null && channel.isConnected()) {
            sendToTerminal(cache);

            this.rows = rows;
            this.cols = cols;
            ((ChannelShell) channel).setPtySize(cols, rows, 0, 0);
            return;
        }
        this.session = session;
        this.rows = rows;
        this.cols = cols;
        this.connection = connection;
        try {
            sshSession = jsch.getSession(connection.getUsername(), connection.getHost(), connection.getPort());
            if (connection.getPrivateKey() != null) {
                if (connection.getPrivateKeyPassword() != null) {
                    jsch.addIdentity("", AES.decode(connection.getPrivateKey()).getBytes(), null, AES.encode(connection.getPrivateKeyPassword()).getBytes());
                } else {
                    jsch.addIdentity("", AES.decode(connection.getPrivateKey()).getBytes(), null, null);
                    // jsch.addIdentity(AES.decode(connection.getPrivateKey()));
                }
            } else {
                sshSession.setPassword(AES.decode(connection.getPassword()));
            }

            sshSession.connect();

            sshSession.setServerAliveInterval(10000);
            channel = sshSession.openChannel("shell");

            executor.execute(() -> {
                byte[] tmp = new byte[1024 * 5];
                try {
                    BufferedInputStream bis = new BufferedInputStream(channel.getInputStream());
                    while (true) {
                        int read = bis.read(tmp, 0, 1024 * 5);
                        if (read < 0) break;
                        String incoming = new String(tmp, 0, read);
                        cache += incoming;
                        if (cache.length() > rows * cols * 35) cache = cache.substring(cache.length() - rows * cols * 35, cache.length());
                        sendToTerminal(incoming);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                sendSetState("closed");
                session.setState("closed");
                cache += "\r\n---press enter to relogin---\r\n\r\n---press escape to close tab---\r\n\r\n";
                sendToTerminal("\r\n---press enter to relogin---\r\n\r\n---press escape to close tab---\r\n\r\n");
            });

            sendToTerminal("Connecting to " + connection.getName() + "...\r\n\r\n");
            ((ChannelShell) channel).setPtyType("xterm", cols, rows, 0, 0);
            channel.connect();
            sendSetState("started");
            session.setState("started");
        } catch (Exception e) {
            cache += e.getMessage() + "\r\n";
            sendToTerminal(e.getMessage() + "\r\n");
            sendSetState("closed");
            session.setState("closed");
        }
    }

    private void sendToTerminal(String text) {
        data.put("command", "terminal");
        data.put("text", text);
        data.remove("state");
        data.remove("sessionId");
        writeToWebSocket(data);
    }

    private void sendSetState(String state) {
        data.put("command", "setState");
        data.put("sessionId", session.getId());
        data.put("state", state);
        data.remove("text");
        writeToWebSocket(data);
    }

    private synchronized void writeToWebSocket(JSONObject data) {
        try {
            if (session.isActive() && session.getWebSocketSession().isOpen()) session.getWebSocketSession().sendMessage(new TextMessage(data.toString()));
        } catch (IOException e) {
            System.out.println("could not write to socket, " + e.getMessage());
        }
    }

    public void send(String key) throws Exception {
        if (channel != null && channel.isConnected()) {
            PrintStream out = new PrintStream(channel.getOutputStream());
            out.print(key);
            out.flush();
        } else {
            if ((int) key.charAt(0) == 13) startConnection(session, connection, rows, cols);
        }
    }

    public void setPtySize(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        ((ChannelShell) channel).setPtySize(cols, rows, 0, 0);
    }

    public void sendCache(com.alessoft.SessionManager.Session session) {
        this.session = session;
        sendToTerminal(cache);
    }

    public void endConnection() {
        if (channel != null) channel.disconnect();
        if (sshSession != null) sshSession.disconnect();
    }


}
