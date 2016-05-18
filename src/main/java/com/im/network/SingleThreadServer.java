package com.im.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SingleThreadServer {
    private int port;

    public SingleThreadServer(int port) {
        this.port = port;
    }

    public void start() {
        System.out.println("Server is running...");
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Waiting for connection...");
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                System.out.println("A new connection is accepted: " + socket.getInetAddress().getHostName());
                this.handleConnection(socket);
            } catch(IOException e) {
                System.out.println("Failed to accept connection: " + e.getMessage());
                System.exit(0);
                return;
            }
        }
    }

    protected void handleConnection(Socket socket) throws IOException {
        BufferedReader in = null;
        PrintWriter    out= null;
        in  = new BufferedReader(new
                InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(),true);
        String         input,output;

        System.out.println("Wait for messages");
        while ((input = in.readLine()) != null) {
            //if (input.equalsIgnoreCase(" ")) break;
            out.println("S ::: "+input);
            System.out.println(input);
        }
        out.close();
        in.close();
        socket.close();

//        PrintWriter out = new PrintWriter(socket.getOutputStream(),
//                true);
//        BufferedReader in = new BufferedReader(
//                new InputStreamReader(socket.getInputStream()));
//
//        String inputLine;
//
//        while ((inputLine = in.readLine()) != null)
//        {
//            System.out.println ("Server: " + inputLine);
//            out.println(inputLine);
//
//            if (inputLine.equals("Bye."))
//                break;
//        }
//
//        out.close();
//        in.close();
//        socket.close();

//        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
//
//            doAnything();
//
//            StringBuilder builder = new StringBuilder();
//            builder.append("Response:\n");
//            String line;
//            while ((line = in.readLine()) != null) {
//                builder.append(line).append("\n");
//            }
//            String str = builder.toString();
//            out.println(str);
//            out.flush();
//        } finally {
//            socket.close();
//        }
    }

    private void doAnything() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        int port = 8081;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        }
        SingleThreadServer server = new SingleThreadServer(port);
        server.start();
    }
}
