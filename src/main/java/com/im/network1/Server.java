package com.im.network1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server extends Thread
{
    public int port;
    private ServerSocket serverSocket = null;
    private boolean isRunning;
    private Map<String, Client> clients;

    public Server() {
        clients = new HashMap<String, Client>();
    }

    public Server(int port) {
        this();
        this.port = port;
    }

    public void bind(int port) throws IOException {
        this.port = port;
        serverSocket = new ServerSocket(port);
        //HttpsServer
    }

    @Override
    public void run() {
        try {
            if (serverSocket == null) bind(port);
        } catch (IOException e) {
            e.printStackTrace();
        }

        isRunning = true;
        while (isRunning) {
            try {
                final Socket clientSocket = serverSocket.accept();
                final Client client = new Client(clientSocket);
                (new Thread() {
                    @Override
                    public void run() {
                        synchronized (clients) {
                            clients.put(client.id, client);
                        }
                        client.start();
                    }
                }).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
