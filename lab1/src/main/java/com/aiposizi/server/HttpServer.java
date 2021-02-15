package com.aiposizi.server;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class HttpServer {

    private static final Logger logger = LogManager.getLogger(HttpServer.class);

    private ServerSocket serverSocket;
    private int port;
    private String rootFolder;

    public HttpServer(int port) {
        this.port = port;
    }

    public void setRootFolder(String rootFolder) {
        this.rootFolder = rootFolder;
    }

    public void start(){
        try {
            serverSocket =new ServerSocket(port);
            logger.log(Level.INFO, "Server started. Waiting for incoming connections on port: " + port);
            while (true){
            Socket acceptSocket =  serverSocket.accept();
            System.out.println("Connection opened. (" + new Date() + ")");
            Thread thread = new Thread(new ServerManager(acceptSocket,rootFolder));
            thread.start();}
        } catch (IOException e) {
            System.err.println("Server connection error : " + e.getMessage());
        }
    }
}
