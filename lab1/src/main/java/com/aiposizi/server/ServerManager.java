package com.aiposizi.server;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Stream;

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
        String fileRequested;
        try {

            String input = in.readLine();
            logger.log(Level.INFO,"Input:\n"+input);

            String[] tokens = input.split("\\s");
            String method = tokens[0].toUpperCase();
            logger.log(Level.INFO, "Request type: " + method);
            fileRequested = tokens[1];
            switch (method){
                case "GET":
                    processGet(fileRequested);
                    break;
                case "POST":
                    processPost(fileRequested);
                    break;
                case "OPTIONS":
                    processOptions(fileRequested);
                    break;
                default:
                    methodNotFound(fileRequested);
            }

        } catch (IOException|NullPointerException e) {
            logger.log(Level.ERROR, "Server error : " + e);
        }finally {
            try {
                in.close();
            }
            catch (IOException e) {
            logger.log(Level.ERROR,"Error closing stream"+e.getMessage()); }
            try {
                out.close();
            } catch (IOException e) {
                logger.log(Level.ERROR,"Error closing stream"+e.getMessage()); }
            try {
                connect.close();
            } catch (IOException e) {
                logger.log(Level.ERROR,"Error closing connect"+e.getMessage()); }

            logger.log(Level.INFO,"Connection closed");

        }
    }

    private void methodNotFound(String fileRequested) {
    }

    private void processOptions(String fileRequested) {
    }


    private void processPost(String fileRequested) {
    }

    private void processGet(String fileRequested) {
    }
}
