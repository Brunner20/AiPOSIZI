package com.aiposizi.server;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.io.*;
import java.net.Socket;
import java.util.Date;

public class ServerManager implements Runnable{

    private static final Logger logger = LogManager.getLogger(ServerManager.class);
    private final Socket connect;
    private BufferedReader in;
    private PrintWriter out;
    private BufferedOutputStream dataOut;


    private static final String FILE_NOT_FOUND = "404.html";
    private static final String METHOD_NOT_IMPLEMENTED= "501.html";
    private static final String ROOT = "./src/main/resources";
    private static final String DEFAULT= "index.html";

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
        String fileRequested = null;
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
                    processPost();
                    break;
                case "OPTIONS":
                    processOptions(fileRequested);
                    break;
                default:
                    methodNotFound(method);
            }

        } catch (FileNotFoundException fnfe) {
            try {
                logger.log(Level.WARN, "File:" + fileRequested + " not found, load " + FILE_NOT_FOUND);
                fileNotFound(fileRequested);
            } catch (IOException ioe) {
                logger.log(Level.WARN, "File error" + ioe.getMessage());
            }
        }
        catch (IOException|NullPointerException e) {
            logger.log(Level.ERROR, "Server error : " + e);
        }finally {
            try {
                in.close();
            } catch (IOException e) {
                logger.log(Level.ERROR,"Error closing stream"+e.getMessage()); }
                out.close();
            try {
                dataOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                connect.close();
            } catch (IOException e) {
                logger.log(Level.ERROR,"Error closing connect"+e.getMessage()); }

            logger.log(Level.INFO,"Connection closed");

        }
    }




    private void methodNotFound(String method) throws IOException {
        logger.log(Level.WARN, "Unknown method: " + method);
        byte[] file =readFileData(new File(ROOT+METHOD_NOT_IMPLEMENTED));
        createResponse(Codes.NOT_IMPLEMENTED, FileType.HTML, file.length, file);
    }

    private void processOptions(String fileRequested) {
        logger.log(Level.INFO, "OPTIONS request accepted");
    }


    private void processPost() throws IOException {
        logger.log(Level.INFO, "POST request accepted");
        createResponse(Codes.CREATED, FileType.PLAIN, 0, new byte[]{});
    }

    private void processGet(String fileRequested) throws IOException {
        logger.log(Level.INFO, "GET request accepted");
        if (fileRequested.endsWith("/")) {
            fileRequested += DEFAULT;
        }
        FileType filetype = FileType.getFileTypeByFilename(fileRequested);
        byte[] file = readFileData(new File(ROOT+fileRequested));
        createResponse(Codes.OK,filetype,file.length,file);

    }

    private byte[] readFileData(File file) throws IOException{

        FileInputStream fileIn;
        byte[] fileData = new byte[(int)file.length()];

            fileIn = new FileInputStream(file);
            fileIn.read(fileData);

            try {
                fileIn.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        return fileData;
    }
    private void fileNotFound(String fileRequested) throws IOException{}


    private void createResponse(Codes code, FileType content, int fileLength, byte[] fileData) throws IOException {
        out.println("HTTP/1.1 " + code.getCode() + " " + code.getDescription());
        out.println("Server: Java HTTP Server");
        out.println("Date: " + new Date());
        out.println("Content-type: " + content.getType());
        out.println("Content-length: " + fileLength);
        out.println("Access-Control-Allow-Origin: " + "localhost");
        out.println("Access-Control-Allow-Methods: " + "GET, POST, OPTIONS");
        out.println();
        out.flush();
        dataOut.write(fileData, 0, fileLength);
        dataOut.flush();
        logger.log(Level.INFO, "type " + content.getExtension() + " size " + fileLength);

        logger.log(Level.INFO, "Creating header of response with code " + code.getCode());
    }
}
