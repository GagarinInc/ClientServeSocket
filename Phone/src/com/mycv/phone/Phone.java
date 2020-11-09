package com.mycv.phone;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Phone implements Closeable {
    private final Socket socket;
    private final BufferedReader reader;
    private final BufferedWriter writer;

    public Phone(ServerSocket serverSocket) throws IOException {
        this.socket = serverSocket.accept();
        this.reader = createReader();
        this.writer = createWriter();

    }

    public Phone(String ip, int port) throws IOException {
        this.socket = new Socket(ip, port);
        this.reader = createReader();
        this.writer = createWriter();

    }

    public void writeLine(String str) throws IOException {
        writer.write(str);
        writer.newLine();
        writer.flush();
    }

    public String readLine() throws IOException {
        return reader.readLine();
    }

    private BufferedReader createReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
    }

    private BufferedWriter createWriter() throws IOException {
        return new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
    }

    @Override
    public void close() throws IOException {
        writer.close();
        reader.close();
        socket.close();
    }
}
