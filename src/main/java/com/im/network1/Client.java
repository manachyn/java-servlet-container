package com.im.network1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.net.Socket;
import java.net.SocketException;

public class Client extends Thread {
    public static final String SOCKET_INPUTSTREAM_ENDED = "end of input stream reached";

    public String id;

    private PushbackInputStream inputStream = null;
    private Socket socket = null;
    private boolean isRunning;


    public Client() {
        super();
    }

    public Client(Socket socket) {
        super();
        setSocket(socket);
    }

    public Socket getSocket() {
        return socket;
    }

    private final void setSocket(Socket socket) {
        this.socket = socket;
        try {
            inputStream = new PushbackInputStream(socket.getInputStream(), 1);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void connect(String hostName, int portNumber) throws Exception {
        setSocket(new Socket(hostName, portNumber));
    }

    @Override
    public void run() {
        isRunning = true;
        System.out.println("Run");
        try {
            int b;
            while (isRunning) {
                if ((b = inputStream.read()) == -1) {
                    throw new SocketException(SOCKET_INPUTSTREAM_ENDED);
                } else {
                    inputStream.unread(b);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void send(byte[] bytes, int offset, int count) throws IOException {
        socket.getOutputStream().write(bytes, offset, count);
    }

    public void send(String message) throws IOException {
        byte[] bytes = message.getBytes();
        send(bytes, 0, bytes.length);
    }
}
