package com.im.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client implements Runnable {
    private String host;
    private int port;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public void run() {
        connect();
    }

    public void connect() {
        try {
            Socket client = new Socket(host, port);
            System.out.println("Connected " + new Date());
            handleConnection(client);
            System.out.println("Close connection " + new Date());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    protected void handleConnection(Socket socket) throws IOException {

        //https://www.cs.uic.edu/~troy/spring05/cs450/sockets/socket.html

        BufferedReader in  = new
                BufferedReader(new
                InputStreamReader(socket.getInputStream()));
        PrintWriter    out = new
                PrintWriter(socket.getOutputStream(),true);
//        BufferedReader inu = new
//                BufferedReader(new InputStreamReader(System.in));

        String fuser,fserver;

        out.print("GET /index.html HTTP/1.1\r\n");
        out.print("Host: localhost:8081\r\n\r\n");

        while ((fserver = in.readLine())!=null) {
//            out.println(fuser);
//            fserver = in.readLine();
            System.out.println(fserver);
//            if (fuser.equalsIgnoreCase("close")) break;
//            if (fuser.equalsIgnoreCase("exit")) break;
        }

        out.close();
        in.close();
//        inu.close();
        socket.close();

//        PrintWriter out = null;
//        BufferedReader in = null;
//
//        out = new PrintWriter(socket.getOutputStream(), true);
//        in = new BufferedReader(new InputStreamReader(
//                socket.getInputStream()));
//
//        out.println("Connection " + new Date());
//
//        StringBuilder builder = new StringBuilder();
//        String line;
//        while (in.ready() && (line = in.readLine()) != null) {
////            line = in.readLine();
//            builder.append(line).append("\n");
//        }
//        String str = builder.toString();
//        System.out.println(str);
//
//
//
//        out.close();
//        in.close();
//
//        socket.close();

//        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
//            out.println("Connection " + new Date());
//
//            StringBuilder builder = new StringBuilder();
//            String line;
//            while ((line = in.readLine()) != null) {
//                builder.append(line).append("\n");
//            }
//            String str = builder.toString();
//            System.out.println(str);
//
//        } finally {
//            socket.close();
//        }
    }


    public static void main(String[] args) {
        String host = "localhost";
        int port = 8081;
        int clientsCount = 5;
        if (args.length > 0) {
            host = args[0];
        }
        if (args.length > 1) {
            port = Integer.parseInt(args[1]);
        }
        ExecutorService executor = Executors.newFixedThreadPool(clientsCount);
        while (true) {
            Client client = new Client(host, port);
            executor.execute(client);
        }
    }


}
