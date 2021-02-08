package com.aiposizi.server;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);
    private static final String HELP = "\n-h --help to print info\n-p --port to set port\n";

    public static void main(String[] args) {

        int port = 8080;
        List<String> arguments =Arrays.asList(args);
        if(!arguments.isEmpty()&&(arguments.get(0).startsWith("-")||arguments.get(0).startsWith("--"))){
            if(arguments.contains("-h")||arguments.contains("--help")){
                logger.log(Level.INFO, HELP);
                return;
            }
            if(arguments.contains("-p")||arguments.contains("--port")){
                String portArg = arguments.get(1);
               try {
                   port = Integer.parseInt(portArg);
                   logger.log(Level.INFO,"New port: "+port);
               }catch (NumberFormatException e){
                   logger.log(Level.WARN,"invalid port. use default port");
               }

            }
        }
        HttpServer server = new HttpServer(port);
        server.start();
    }
}
