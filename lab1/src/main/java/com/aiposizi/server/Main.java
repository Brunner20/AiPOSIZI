package com.aiposizi.server;

public class Main {

    public static void main(String[] args) {

        HttpServer server = new HttpServer(8080);
        server.start();
    }
}
