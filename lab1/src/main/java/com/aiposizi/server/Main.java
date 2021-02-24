package com.aiposizi.server;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);
    private static final String HELP = "\n-h --help to print info\n-p --port to set port\n-r --root to set root folder";

    public static void main(String[] args) {

        int port = 8080;
        String root= "./lab1/src/main/resources";
        List<String> arguments =Arrays.asList(args);
        if(!arguments.isEmpty()&&(arguments.get(0).startsWith("-")||arguments.get(0).startsWith("--"))){
            if(arguments.contains("-h")||arguments.contains("--help")){
                logger.log(Level.INFO, HELP);
                return;
            }
            if(arguments.contains("-p")||arguments.contains("--port")) {
                int argNum = -1;
                if ((argNum = arguments.indexOf("-p")) > -1)
                    argNum++;
                else if ((argNum = arguments.indexOf("--port")) > -1)
                    argNum++;
                if (argNum != -1) {
                    try {
                        port = Integer.parseInt(arguments.get(argNum));
                        logger.log(Level.INFO, "New port: " + port);
                    } catch (NumberFormatException e) {
                        logger.log(Level.WARN, "invalid port. use default port 8080");
                    }
                }
            }
            if(arguments.contains("-r")||arguments.contains("--root")){
                int  argNum=-1;
                if((argNum = arguments.indexOf("-r"))>-1)
                    argNum++;
                else if((argNum = arguments.indexOf("--root"))>-1)
                    argNum++;
                if(argNum!=-1)
                    { root = arguments.get(argNum);
                    logger.log(Level.INFO,"New root: "+root);}


            }
        }


        HttpServer server = new HttpServer(port);
        server.setRootFolder(root);
        server.start();
    }
}
