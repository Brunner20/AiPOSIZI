package com.aiposizi.server;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.Socket;

public class ServerManager implements Runnable{

    private static final Logger logger = LogManager.getLogger(ServerManager.class);
    private Socket connect;
    private BufferedReader in;
    private ObjectOutputStream out;

    public ServerManager(Socket connect) {
        this.connect = connect;
        try {
            in = new BufferedReader(new InputStreamReader(connect.getInputStream()));
            out = new ObjectOutputStream(connect.getOutputStream());
        } catch (IOException e) {
            logger.log(Level.ERROR, "Server error : " + e);
        }
    }

    @Override
    public void run() {

        try {
            String input = in.readLine();
            logger.log(Level.INFO,"Input:\n"+input);
        } catch (IOException e) {
            logger.log(Level.ERROR, "Server error : " + e);
        }
    }
}
