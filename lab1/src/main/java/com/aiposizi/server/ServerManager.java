package com.aiposizi.server;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.print.Printable;
import java.io.*;
import java.net.Socket;


public class ServerManager implements Runnable{

    private static final Logger logger = LogManager.getLogger(ServerManager.class);
    private Socket connect;
    private BufferedReader in;
    private PrintWriter out; //поменял тип на PrintWriter, хз не испортит это че-нить, но дальше нужен PrintWriter
    private BufferedOutputStream dataOut;

    private static final String ROOT = "/sharedFiles";
    private static final String DEFAULT_FILE = "index.html";
    private static final String FILE_NOT_FOUND = "404.html";
    private static final String METHOD_NOT_SUPPORTED = "501.html";

    public ServerManager(Socket connect) {
        this.connect = connect;
        try {
            in = new BufferedReader(new InputStreamReader(connect.getInputStream()));
            out = new PrintWriter(connect.getOutputStream());
            dataOut = new BufferedOutputStream(connect.getOutputStream());
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
            } catch (IOException e) {
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

  

    private InputStream findFile(String fileName, boolean clientFile) throws FileNotFoundException {
        if (clientFile) {
            fileName = ROOT + fileName;
        } else {
            fileName = "/" + fileName;
        }
        InputStream inputStream = this.getClass().getResourceAsStream(fileName);
        logger.log(Level.INFO, "requested path of the file: " + this.getClass().getResource(fileName));
        if (inputStream == null) {
            throw new FileNotFoundException();
        }
        return inputStream;
    }

    private void createResponse(Codes code, Content content, int fileLength, byte[] fileData) throws IOException {
        out.println("HTTP/1.1 " + code.getCode() + " " + code.getDescription());
        out.println("Server: Java HTTP Server");
        out.println("Date: " + new Date());
        out.println("Content-type: " + content.getText());
        out.println("Content-length: " + fileLength);
        out.println("Access-Control-Allow-Origin: " + "localhost");
        out.println("Access-Control-Allow-Methods: " + "GET, POST, OPTIONS");
        out.println();
        out.flush();
        dataOut.write(fileData, 0, fileLength);
        dataOut.flush();
        logger.log(Level.INFO, "type " + content.getExtension() + " size " + fileLength);
        try {
            Thread.sleep(fileLength / 100);
        } catch (InterruptedException e) {

        }
        logger.log(Level.INFO, "Creating header of response with code " + code.getCode());
    }

    private void methodNotFound(String fileRequested) {
    }

    private void processOptions(String fileRequested) {
    }


    private void processPost(String fileRequested) {
    }

    private void processGet(String fileRequested) {
       logger.log(Level.INFO, "GET request accepted");
        if (fileRequested.endsWith("/")) {
            fileRequested += DEFAULT_FILE;
        }
        InputStream inputStream = findFile(fileRequested, true);
        Content content = Content.findByFileName(fileRequested);
        byte[] data = content.getReader().read(inputStream);
        createResponse(Codes.OK, content, data.length, data);
        logger.log(Level.INFO, "File " + fileRequested + " of type " + content.getText() + " returned");
    }

}
